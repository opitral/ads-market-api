package com.opitral.ads.market.api.services.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.opitral.ads.market.api.domain.entity.GroupEntity;
import com.opitral.ads.market.api.domain.enums.DateTimeStatus;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.model.response.ScheduleDateTimeResponse;
import com.opitral.ads.market.api.model.response.ScheduleResponse;
import com.opitral.ads.market.api.repositories.GroupRepository;
import com.opitral.ads.market.api.repositories.PostRepository;
import com.opitral.ads.market.api.services.group.GroupService;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
@RequiredArgsConstructor
public class ScheduleService {

    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final GroupService groupService;

    private static final int SCHEDULE_DAYS_COUNT = 30;

    public ScheduleResponse getSchedule(Integer groupId) {
        GroupEntity group = getGroupById(groupId);
        List<ScheduleDateTimeResponse> dateTimes = buildDateTimeResponses(group);

        return ScheduleResponse.builder()
                .group(groupService.buildGroupResponseDto(group))
                .dateTimes(dateTimes)
                .build();
    }

    private GroupEntity getGroupById(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchEntityException(GroupEntity.class.getName(), "by id: " + groupId));
    }

    private List<ScheduleDateTimeResponse> buildDateTimeResponses(GroupEntity group) {
        List<ScheduleDateTimeResponse> dateTimes = new ArrayList<>();

        for (int i = 0; i < SCHEDULE_DAYS_COUNT; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            LocalTime time = group.getWorkingHoursStart();

            while (!time.isAfter(group.getWorkingHoursEnd())) {
                DateTimeStatus status = determineStatus(group, date, time);
                dateTimes.add(buildDateTimeResponse(date, time, status));
                time = time.plusMinutes(group.getPostIntervalInMinutes());
            }
        }
        return dateTimes;
    }

    private DateTimeStatus determineStatus(GroupEntity group, LocalDate date, LocalTime time) {
        if (!date.isAfter(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            return DateTimeStatus.UNAVAILABLE;
        } else if (postRepository.existsByGroupIdAndPublishDateAndPublishTime(group.getId(), date, time)) {
            return DateTimeStatus.BUSY;
        } else {
            return DateTimeStatus.FREE;
        }
    }

    private ScheduleDateTimeResponse buildDateTimeResponse(LocalDate date, LocalTime time, DateTimeStatus status) {
        return ScheduleDateTimeResponse.builder()
                .date(date)
                .time(time)
                .status(status)
                .build();
    }
}
