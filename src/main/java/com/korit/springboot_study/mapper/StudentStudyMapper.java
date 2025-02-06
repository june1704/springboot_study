package com.korit.springboot_study.mapper;

import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentStudyMapper {

    List<Major> selectMajorsAll();
    List<Instructor> selectInstructorAll();

    int insertMajor(Major major);
    int insertInstructor(Instructor instructor);

    int updateMajorName(Major major);
}

/*
실제로 이 메서드는 **"MajorName을 업데이트"**하는 동작을 수행하는 것이므로,
쿼리가 몇 개의 행을 변경했는지를 int로 반환합니다.
행의 갯수를 변경하기 위해 int를 씀

✔ List<>를 사용하면 여러 개의 데이터를 한 번에 반환할 수 있음.
✔ MyBatis의 SQL 실행 결과가 여러 개의 행(Row)일 가능성이 높음.
✔ 코드의 유지보수성과 확장성을 높이기 위해 List<>를 사용해야 함.

✅ 즉, 여러 개의 데이터를 반환하기 위해 List<>를 사용하는 것입니다!
*/