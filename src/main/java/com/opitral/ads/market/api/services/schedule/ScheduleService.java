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
import com.opitral.ads.market.api.domain.enums.ScheduleStatus;
import com.opitral.ads.market.api.exception.BaseException;
import com.opitral.ads.market.api.exception.NoSuchEntityException;
import com.opitral.ads.market.api.repositories.GroupRepository;
import com.opitral.ads.market.api.repositories.PostRepository;
import com.opitral.ads.market.api.services.group.GroupService;
import com.opitral.ads.market.api.model.response.*;


@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BaseException.class)
@RequiredArgsConstructor
public class ScheduleService {

    private final PostRepository postRepository;
    private final GroupRepository groupRepository;
    private final GroupService groupService;

    private static final int WEEKS_COUNT = 5;
    private static final int DAYS_IN_WEEK = 7;

    public ScheduleResponse getSchedule(Integer groupId) {
        GroupEntity group = getGroupById(groupId);
        List<WeekResponse> weeks = buildWeekResponses(group);

        return ScheduleResponse.builder()
                .group(groupService.buildGroupResponseDto(group))
                .weeks(weeks)
                .build();
    }

    private GroupEntity getGroupById(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchEntityException(GroupEntity.class.getName(), "by id: " + groupId));
    }

    private List<WeekResponse> buildWeekResponses(GroupEntity group) {
        List<WeekResponse> weeks = new ArrayList<>();

        for (int week = 0; week < WEEKS_COUNT; week++) {
            List<DayResponse> days = new ArrayList<>();
            for (int day = 0; day < DAYS_IN_WEEK; day++) {
                LocalDate date = LocalDate.now().plusDays(week * DAYS_IN_WEEK + day);
                List<TimeResponse> times = buildTimeResponses(group, date);
                days.add(DayResponse.builder()
                        .date(date)
                        .times(times)
                        .build());
            }
            weeks.add(WeekResponse.builder()
                    .days(days)
                    .build());
        }

        return weeks;
    }

    private List<TimeResponse> buildTimeResponses(GroupEntity group, LocalDate date) {
        List<TimeResponse> times = new ArrayList<>();
        LocalTime time = group.getWorkingHoursStart();

        while (!time.isAfter(group.getWorkingHoursEnd())) {
            ScheduleStatus status = determineStatus(group, date, time);
            times.add(TimeResponse.builder()
                    .time(time)
                    .status(status)
                    .build());
            time = time.plusMinutes(group.getPostIntervalInMinutes());
        }

        return times;
    }

    private ScheduleStatus determineStatus(GroupEntity group, LocalDate date, LocalTime time) {
        if (!date.isAfter(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            return ScheduleStatus.UNAVAILABLE;
        } else if (postRepository.existsByGroupIdAndPublishDateAndPublishTime(group.getId(), date, time)) {
            return ScheduleStatus.BUSY;
        } else {
            return ScheduleStatus.FREE;
        }
    }
}
