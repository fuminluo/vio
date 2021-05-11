package priv.rabbit.vio;


import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.rabbit.vio.config.redis.DistributedLocker;
import priv.rabbit.vio.entity.Department;
import priv.rabbit.vio.entity.Employee;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.mapper.EmployeeMapper;
import priv.rabbit.vio.mapper.UserMapper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class VioApplicationTests {


    @Autowired
    DistributedLocker distributedLocker;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    private KieSession session;

    @Autowired
    private KieBase kieBase;

    /**
     * 读取mybatis的配置文件，生成SqlSessionFactory
     *
     * @return
     */
    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;
        String resource = "mybatisConfig.xml";
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources
                    .getResourceAsReader(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }



    @Test
    public void findUserById() {
       // ReuseExecutor executor = new ReuseExecutor();
       // executor.query()
    }

    @Test
    public void contextLoads() {
        User people = new User();
        people.setUsername("people");
        people.setSex(1);
        session.insert(people);//插入
        session.fireAllRules();//执行规则

    }

    void employee() {
        Employee employee = employeeMapper.getEmpAndDept(1);
        System.out.println("===getDepartmentName===" + employee.getDept().getDepartmentName());

        Department department = employeeMapper.getDeptByIdPlus(1);

        List<Employee> list = employeeMapper.getEmpAndDepts(1);

        System.out.println("===getEmps===" + list.get(0).getDept().getDepartmentName());
    }


    void lock() {

        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.err.println("=============线程开启============" + Thread.currentThread().getName());
                        /*
                         * distributedLocker.lock(key,10L); //直接加锁，获取不到锁则一直等待获取锁
                         * Thread.sleep(100); //获得锁之后可以进行相应的处理
                         * System.err.println("======获得锁后进行相应的操作======"+Thread.
                         * currentThread().getName());
                         * distributedLocker.unlock(key); //解锁
                         * System.err.println("============================="+
                         * Thread.currentThread().getName());
                         */
                        boolean isGetLock = distributedLocker.tryLock(key, TimeUnit.SECONDS, 5L, 10L); // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        if (isGetLock) {
                            System.out.println("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                            Thread.sleep(100); // 获得锁之后可以进行相应的处理
                            System.err.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName());
                            //distributedLocker.unlock(key);
                            System.err.println("=============================" + Thread.currentThread().getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
    }

  /*  @Test
    public void generateAsciiDocs() throws MalformedURLException {
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();
        Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs")).withConfig(config).build()
                .toFile(Paths.get("src/docs/asciidoc/generated/all"));
    }

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void createSpringfoxSwaggerJson() throws Exception {

        String outputDir = System.getProperty("io.springfox.staticdocs.outputDir");
        MvcResult mvcResult = this.mockMvc.perform(get("/v2/api-docs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String swaggerJson = response.getContentAsString();
        Files.createDirectories(Paths.get(outputDir));
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputDir, "swagger.json"), StandardCharsets.UTF_8)) {
            writer.write(swaggerJson);
        }
    }*/

}
