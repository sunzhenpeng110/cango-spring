package com.cango.Controller;

import com.cango.Service.UserService;
import com.cango.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Registration registration;

    @Autowired
    private DiscoveryClient client;

//    @Autowired
//    private UserService userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        ServiceInstance instance = serviceInstance();
        //logger.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "say hello :" + instance.getHost();
    }

    public ServiceInstance serviceInstance() {
        List<ServiceInstance> list = client.getInstances(registration.getServiceId());
        if (list != null && list.size() > 0) {
            for (ServiceInstance itm : list) {
                if (itm.getPort() == 6666) {
                    return itm;
                }
            }
        }
        return null;
    }

    // 链接数据库
    @RequestMapping(value = "/searchUser")
    public String searchUser(ModelMap map) {
//        List<User> userList = userService.searchUser();
//        map.addAttribute("userList", userList);
        return "searchUser";
    }

}
