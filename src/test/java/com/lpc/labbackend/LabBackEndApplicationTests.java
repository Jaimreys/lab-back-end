package com.lpc.labbackend;

import com.lpc.labbackend.dao.MenuMapper;
import com.lpc.labbackend.entity.Menu;
import com.lpc.labbackend.entity.ResponseData;
import com.lpc.labbackend.enumeration.HttpStatusEnum;
import com.lpc.labbackend.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LabBackEndApplicationTests {
    @Autowired
    private MenuMapper menuMapper;

    @Test
    void contextLoads() {
        List<Menu> menus = menuMapper.getMenus();
        ResponseData<List<Menu>> data = new ResponseData<>(menus, HttpStatusEnum.SUCCESSFUL);
        String jsonString = JsonUtil.responseData2JsonString(data);
        System.out.println(jsonString);
    }

}
