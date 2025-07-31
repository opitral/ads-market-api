package com.opitral.ads.market.api.services.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.opitral.ads.market.api.domain.entity.PostEntity;
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

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(WEEKS_COUNT * DAYS_IN_WEEK);
        Map<LocalDate, Set<LocalTime>> busySlots = loadBusySlots(group.getId(), startDate, endDate);

        List<WeekResponse> weeks = buildWeekResponses(group, busySlots, startDate);

        return ScheduleResponse.builder()
                .group(groupService.buildGroupResponseDto(group))
                .weeks(weeks)
                .build();
    }

    private GroupEntity getGroupById(Integer groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NoSuchEntityException(GroupEntity.class.getName(), "by id: " + groupId));
    }

    private Map<LocalDate, Set<LocalTime>> loadBusySlots(Integer groupId, LocalDate from, LocalDate to) {
        List<PostEntity> posts = postRepository.findAllByGroupIdAndPublishDateBetween(groupId, from, to);
        return posts.stream()
                .collect(Collectors.groupingBy(
                        PostEntity::getPublishDate,
                        Collectors.mapping(PostEntity::getPublishTime, Collectors.toSet())
                ));
    }

    private List<WeekResponse> buildWeekResponses(GroupEntity group, Map<LocalDate, Set<LocalTime>> busySlots, LocalDate startDate) {
        List<WeekResponse> weeks = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        for (int week = 0; week < WEEKS_COUNT; week++) {
            List<DayResponse> days = new ArrayList<>();
            for (int day = 0; day < DAYS_IN_WEEK; day++) {
                LocalDate date = startDate.plusDays(week * DAYS_IN_WEEK + day);
                List<TimeResponse> times = buildTimeResponses(group, date, busySlots, today, now);
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

    private List<TimeResponse> buildTimeResponses(GroupEntity group, LocalDate date, Map<LocalDate, Set<LocalTime>> busySlots, LocalDate today, LocalTime now) {
        List<TimeResponse> times = new ArrayList<>();
        LocalTime startTime = group.getWorkingHoursStart();
        LocalTime endTime = group.getWorkingHoursEnd();

        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        if (endTime.equals(LocalTime.MIDNIGHT)) {
            endDateTime = endDateTime.plusDays(1);
        }

        LocalDateTime tempDateTime = startDateTime;
        int interval = group.getPostIntervalInMinutes();

        while (tempDateTime.isBefore(endDateTime)) {
            LocalTime currentTime = tempDateTime.toLocalTime();
            ScheduleStatus status;

            if (!date.isAfter(today) && currentTime.isBefore(now)) {
                status = ScheduleStatus.UNAVAILABLE;
            } else if (busySlots.getOrDefault(date, Set.of()).contains(currentTime)) {
                status = ScheduleStatus.BUSY;
            } else {
                status = ScheduleStatus.FREE;
            }

            times.add(TimeResponse.builder()
                    .time(currentTime)
                    .status(status)
                    .build());

            tempDateTime = tempDateTime.plusMinutes(interval);
        }

        return times;
    }
}
