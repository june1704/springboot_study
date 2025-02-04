package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.study.ReqAddStudentDto;
import com.korit.springboot_study.dto.request.study.ReqStudentDto;
import com.korit.springboot_study.dto.response.study.RespAddStudentDto;
import com.korit.springboot_study.dto.response.study.RespStudentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController // 모든 것에 대해 데이터 응답을 받을 것이다.
@Api(tags = "REST API 수업") // 해당 컨트롤러의 이름
public class FirstRestController {

    @ApiOperation(value = "api hello", notes = "헬로우 에브리 원?") // 메소드 위에
    @GetMapping("/api/hello")
    public Map<String, Object> hello(HttpServletRequest request) {
        String age = request.getParameter("age");
        String address = request.getParameter("address");

        System.out.println(age);
        System.out.println(address);

        return Map.of("name", "최명준");
    }


    @GetMapping("/api/hello2")
    public Map<String, Object> hello2(@RequestParam int age, @RequestParam String address) {

        System.out.println(age);
        System.out.println(address);

        return Map.of("name", "최명준");
    }

    // /api/student/1
    @ApiOperation(value = "학생조회(일반 for 선형탐색)", notes = "일반 for문을 사용하여 선형 탐색학습")
    @GetMapping("/api/student")
    public Map<String, Object> getStudent(
            @ApiParam(value = "조회할 학생 인덱스", required = false)
            @RequestParam(required = false) int studentId
    ) {
        List<Map<String, Object>> students = new ArrayList<>();
        students.add(Map.of("id", 11, "name", "최석현", "age", 26));
        students.add(Map.of("id", 22, "name", "백진우", "age", 32));
        students.add(Map.of("id", 33, "name", "이주원", "age", 28));
        students.add(Map.of("id", 44, "name", "정영훈", "age", 26));

        int foundIndex = -1;

        for (int i = 0; i < students.size(); i++) {
            if ((Integer) students.get(i).get("id") == studentId) {
                foundIndex = i;
                break;
            }
        }

        if (foundIndex == -1) {
            return Map.of("error", "찾지 못했음");
        }

        return students.get(foundIndex);
    }

    @ApiOperation(value = "아 놀고 싶어라", notes = "한 일주일만 어떻게 더 안되겠니?")
    @GetMapping("/api/student2")
    public Map<String, Object> getStudent2(
            @ApiParam(value = "조회할 학생 인덱스", required = true)
            @RequestParam int studentId
    ) {
        List<Map<String, Object>> students = new ArrayList<>();
        students.add(Map.of("id", 11, "name", "최석현", "age", 26));
        students.add(Map.of("id", 22, "name", "백진우", "age", 32));
        students.add(Map.of("id", 33, "name", "이주원", "age", 28));
        students.add(Map.of("id", 44, "name", "정영훈", "age", 26));

        Map<String, Object> foundStudent = null;

        for (Map<String, Object> student : students) {
            if ((Integer) student.get("id") == studentId) {
                foundStudent = student;
                break;
            }
        }
        if (foundStudent == null) {
            return Map.of("error", "찾지 못했음");
        }
        return foundStudent;
    }

    @ApiOperation(value = "이제껏 놀았는데 더 놀고 싶어요 강사님...", notes = "일년간 놀라고 하시면 안될까요?")
    @GetMapping("/api/student3")
    public Map<String, Object> getStudent3(
            @ApiParam(value = "조회할 학생 인덱스", required = true)
            @RequestParam int studentId
    ) {
        List<Map<String, Object>> students = new ArrayList<>();
        students.add(Map.of("id", 11, "name", "최석현", "age", 26));
        students.add(Map.of("id", 22, "name", "백진우", "age", 32));
        students.add(Map.of("id", 33, "name", "이주원", "age", 28));
        students.add(Map.of("id", 44, "name", "정영훈", "age", 26));

        Map<String, Object> responseData = students.stream()
                .filter(student -> (Integer) student.get("id") == studentId)
                .findFirst()
                .orElse(Map.of("error", "찾지 못했음"));

        return responseData;
    }
// 해당 컨트롤러의 설명서를 만들기 위해 swagger사용
    @GetMapping("api/student4/{studentId}")
    public RespStudentDto getStudent4(
            // @PathVariable: 경로의 변수
            @ApiParam(value = "학생 ID", required = true)
            @PathVariable int studentId,
            ReqStudentDto reqStudentDto) {

        return new RespStudentDto(100, "김준이", 33);
    }

    @PostMapping("/api/student")
    @ApiOperation(value = "학생 추가", notes = "학생 정보를 입력받아 user_tb에 데이터를 저장합니다.")
    public ResponseEntity<RespAddStudentDto> addStudent(@RequestBody ReqAddStudentDto reqAddStudentDto) { // @RequestBody
        System.out.println(reqAddStudentDto);
//        return ResponseEntity.ok().body(new RespAddStudentDto("학생 추가 완료", true));
        return ResponseEntity.badRequest().body(new RespAddStudentDto("학생 추가 실패", false));
    }

    @PutMapping("/api/student/{studentId}")
    @ApiOperation(value = "학생 정보 수정", notes = "학생 ID를 기준으로 학생 정보를 수정합니다.")
    public ResponseEntity<?> updateStudent(@ApiParam(value = "학생 ID", example = "1", required = true)
           @PathVariable int studentId,
           @RequestBody Map<String, Object> reqBody) {

        System.out.println(reqBody);

        return ResponseEntity.ok().body(null);
    }

    // delete일 때는 파라미터를 쓰지 않는다.
    @ApiOperation(value = "학생 정보 삭제", notes = "학생 ID를 기준으로 정보를 삭제합니다.")
    @DeleteMapping("/api/student/{studentId}")
    public ResponseEntity<?> deleteStudent(@RequestParam int studentId) {

        return ResponseEntity.ok().body(null);
    }

}



