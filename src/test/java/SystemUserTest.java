import com.fasterxml.jackson.databind.ObjectMapper;
import com.lpc.LabBackEndApplication;
import com.lpc.entity.enumeration.RoleEnum;
import com.lpc.entity.pojo.SystemUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 居然可以绕过token
 * <p>
 * 1 @RequestBody
 * MockHttpServletRequestBuilder mockHttpServletRequestBuilder
 * = MockMvcRequestBuilders
 * .post("/user")
 * .contentType(MediaType.APPLICATION_JSON)
 * .content(objectMapper.writeValueAsString(requestBody));
 * <p>
 * 2 @RequesetParam
 * MockHttpServletRequestBuilder mockHttpServletRequestBuilder
 * = MockMvcRequestBuilders
 * .delete("/user")
 * .contentType(MediaType.APPLICATION_FORM_URLENCODED)
 * .param("username", String.valueOf(30012));
 * <p>
 * 3 @PathVariable
 * MockHttpServletRequestBuilder mockHttpServletRequestBuilder
 * = MockMvcRequestBuilders
 * .delete("/user/{username}","12345")
 * .contentType(MediaType.APPLICATION_FORM_URLENCODED);
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LabBackEndApplication.class)
public class SystemUserTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * 添加用户的测试
     * 需要@RequestBody
     */
    @Test
    public void addUserTest() throws Exception {
        // 报错：Invalid JSON input: Unrecognized field "enabled" (class com.lpc.entity.pojo.SystemUser), not marked as ignorable;
        // 因为这个类是继承过来的，但是序列化的时候把父类的属性也加到JSON。
        // 反序列化的时候这样却不行，说认不出enabled这个属性。佛了
        SystemUser newUser = new SystemUser();
        newUser.setUsername(30012L);
        newUser.setRealName("詹姆斯·高斯林");
        newUser.setPassword(bCryptPasswordEncoder.encode("123456"));
        newUser.setRole(RoleEnum.STUDENT.getRole());
        // 所以要用map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", 30012L);
        requestBody.put("realName", "詹姆斯·高斯林");
        requestBody.put("password", bCryptPasswordEncoder.encode("123456"));
        requestBody.put("role", RoleEnum.STUDENT.getRole());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder
                = MockMvcRequestBuilders
                // 发一个post请求，请求的url是/user
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                // 带上请求体
                .content(objectMapper.writeValueAsString(requestBody));
        // 发送请求
        mockMvc.perform(mockHttpServletRequestBuilder)
                // 判断相应结果是否和自己预期相符
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 打印出请求和响应的具体内容，打出来的中文是乱码
                .andDo(MockMvcResultHandlers.print());
        // 还可以获取到解结果
        // MvcResult mvcResult = mockMvc.perform(mockHttpServletRequestBuilder).andReturn();
    }

    /**
     * 删除一个用户的测试
     */
    @Test
    public void deleteSystemUserTest() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder
                = MockMvcRequestBuilders
                // 发一个get请求，请求的url是/users
                .delete("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                // param带的就是后台@RequestParam接收的参数
                .param("username", String.valueOf(30012));
        // 发送请求
        mockMvc.perform(mockHttpServletRequestBuilder)
                // 判断相应结果是否和自己预期相符
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 打印出请求和响应的具体内容
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 获取所有用户的测试
     * 需要@RequestParam
     */
    @Test
    public void getSystemUsersTest() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder
                = MockMvcRequestBuilders
                // 发一个get请求，请求的url是/users
                .get("/users")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                // param带的就是后台@RequestParam接收的参数
                .param("pageNum", String.valueOf(1))
                .param("pageSize", String.valueOf(5))
                .param("realName", "");
        // 发送请求，并获取响应
        // 直接打印出来会有乱码，所以要设置字符集
        MvcResult mvcResult = mockMvc
                .perform(mockHttpServletRequestBuilder)
                .andReturn();
        String contentAsString = mvcResult
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        System.out.println(contentAsString);
    }

    /**
     * 修改系统用户角色的测试
     */
    @Test
    public void updateUserRoleTest() throws Exception {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("username", 30012L);
        requestBody.put("role", RoleEnum.TEACHER.getRole());

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder
                = MockMvcRequestBuilders
                // 发一个get请求，请求的url是/users
                .put("/user/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody));
        // 发送请求
        mockMvc.perform(mockHttpServletRequestBuilder)
                // 判断相应结果是否和自己预期相符
                .andExpect(MockMvcResultMatchers.status().isOk())
                // 打印出请求和响应的具体内容
                .andDo(MockMvcResultHandlers.print());
    }

}
