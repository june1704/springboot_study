package com.korit.springboot_study.entity.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // get, set을 mapper에서 사용하기 위해
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Major {
    private int majorId;
    private String majorName;
}
