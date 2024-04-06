package com.ehealthss.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ehealthss.model.DoctorAttendance;
import com.ehealthss.model.DoctorSchedule;
import com.ehealthss.model.User;
import com.ehealthss.service.AttendanceService;
import com.ehealthss.service.DoctorAttendanceService;
import com.ehealthss.service.DoctorScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Autowired
	private final DoctorScheduleService doctorScheduleService;
	private final DoctorAttendanceService doctorAttendanceService;

	public AttendanceServiceImpl(DoctorScheduleService doctorScheduleService,
			DoctorAttendanceService doctorAttendanceService) {

		this.doctorScheduleService = doctorScheduleService;
		this.doctorAttendanceService = doctorAttendanceService;

	}

	@Override
	public String index(Model model, User user) {

		String template = "attendance/attendance";

		model.addAttribute("pageTitle", "Attendance");
		model.addAttribute("withCalendarComponent", true);
		model.addAttribute("withTableComponent", true);

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
		model.addAttribute("dateToday", formatter.format(localDate));

		// Get doctor schedules
		List<DoctorSchedule> doctorSchedules = doctorScheduleService.findByDoctorId(user.getDoctor().getId());

		for (DoctorSchedule doctorSchedule : doctorSchedules) {

			if (doctorSchedule.getDayOfWeek().toString().equals(localDate.getDayOfWeek().toString().substring(0, 3))) {
				ObjectMapper mapper = new ObjectMapper();

				try {
					// Check if doctor has attendance today
					DoctorAttendance doctorAttendance = doctorAttendanceService.findByDoctorIdAndLocationIdAndDate(
							doctorSchedule.getDoctor().getId(), doctorSchedule.getLocation().getId(), Date.valueOf(localDate));

					if (doctorAttendance != null) {
						model.addAttribute("hasAttendance", true);
					}
					
					model.addAttribute("scheduleToday",
							Base64.getEncoder().encodeToString(mapper.writeValueAsString(doctorSchedule).getBytes()));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}

				break;
			}
		}

		return template;

	}

	@Override
	public void create(User user, int scheduleId, DoctorAttendance doctorAttendance) {

		DoctorSchedule doctorSchedule = doctorScheduleService.getReferenceById(scheduleId);

		doctorAttendance.setDoctor(user.getDoctor());
		doctorAttendance.setLocation(doctorSchedule.getLocation());
		doctorAttendanceService.save(doctorAttendance);

	}

}
