package com.bonaparte.service;

import com.bonaparte.dao.mapper.ChargeMapper;
import com.bonaparte.entity.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yangmingquan on 2018/6/29.
 * lamda 函数是编程及高级功能
 */
@Service
public class LamdaProgramService {
    @Autowired
    private ChargeMapper chargeMapper;

    public void lamdaProgram(){
        List<Charge> list1 = chargeMapper.selectAll();
        List<Charge> list2 = chargeMapper.selectAll();
        //惰性求值
        list1.stream().filter(x -> x.getStatus() == 1);
        //及早求值
        list1 = list1.stream()
                .filter(x -> x.getStatus() == 1)
                .collect(Collectors.toList());
        list1.stream()
                .parallel()
                .forEach(x -> x.setMoney(1.0));
        list1.parallelStream()
                .map(x -> x.getMoney() + 1)
                .collect(Collectors.toList());

        //合并两个数组
        Stream.of(list1, list2)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
        //找出缴费最多的那个缴费记录
        list1.stream()
                .max(Comparator.comparing(x->x.getMoney()))
                .get();
        list1.stream()
        //找出缴费最少的那个缴费记录
                .min(Comparator.comparing(x->x.getMoney()));
        //判断是由有人缴费1块
        list1.stream()
                .anyMatch(x->x.getMoney()==1);
        //去重
        list1.stream()
                .distinct()
                .collect(Collectors.toList());
        //查找满足条件的数据
        Charge charge = new Charge();
        list1.stream()
                .findFirst()
                .equals(charge);

        //求缴费的平均值
        list1.stream()
                .mapToDouble(x->x.getMoney())
                .average();

        //根据缴费金额排序
        list1.stream()
                .sorted(Comparator.comparing(x->x.getMoney()))
                .collect(Collectors.toList());
    }
}
