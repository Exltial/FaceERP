package com.ncut.face.erp.bootstrap;

import com.ncut.face.erp.service.user.domain.FaceIdModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class NormalTest {
    @Test
    public void test() {
        List<FaceIdModel> list = new ArrayList<>();
        list.add(new FaceIdModel().setScore(20));
        list.add(new FaceIdModel().setScore(30));
        list.add(new FaceIdModel().setScore(90));
        list.add(new FaceIdModel().setScore(80));
        list.add(new FaceIdModel().setScore(70));
        List<FaceIdModel> collect = list.parallelStream().filter(item -> {
            return item.getScore() > 50;
        }).sorted(Comparator.comparing(FaceIdModel::getScore).reversed()).collect(Collectors.toList());
        System.out.println(collect);
    }
}
