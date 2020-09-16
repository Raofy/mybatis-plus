package com.ryan.crud.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.ryan.crud.service.IUserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService iUserService;

    /**
     * 插入一条记录
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestBody User user) {
        return iUserService.save(user);
    }

    /**
     * 批量插入记录
     *
     * @param user
     * @return
     */
    @PostMapping("/addBatch1")
    public Object addBatch1(@RequestBody List<User> user) {
        return iUserService.saveBatch(user);
    }

    /**
     * 批量插入记录
     *
     * @param user
     * @return
     */
    @PostMapping("/addBatch2")
    public Object addBatch2(@RequestBody List<User> user) {
        return iUserService.saveBatch(user, user.size());
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdate1")
    public Object saveOrUpdate1(@RequestBody User user) {
        return iUserService.saveOrUpdate(user);
    }

    /**
     * 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
     *
     * @param user
     * @return
     */
    @PostMapping("/saveOrUpdate2")
    public Object saveOrUpdate2(@RequestBody User user) {
        return iUserService.saveOrUpdate(user, new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

    /**
     * 批量修改更新
     *
     * @param users
     * @return
     */
    @PostMapping("/saveOrUpdateBatch1")
    public Object saveOrUpdateBatch1(@RequestBody List<User> users) {
        return iUserService.saveOrUpdateBatch(users);
    }

    /**
     * 批量修改更新
     *
     * @param users
     * @return
     */
    @PostMapping("/saveOrUpdateBatch2")
    public Object saveOrUpdateBatch2(@RequestBody List<User> users) {
        return iUserService.saveOrUpdateBatch(users, users.size());
    }

    /**
     * 根据 entity 条件，删除记录
     *
     * @return
     */
    @PostMapping("/remove")
    public Object remove() {
        return iUserService.remove(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

    /**
     * 根据Id进行删除
     *
     * @param id
     * @return
     */
    @PostMapping("/removeById")
    public Object removeById(@RequestBody int id) {
        return iUserService.removeById(id);
    }

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param map
     * @return
     */
    @PostMapping("/removeByMap")
    public Object removeByMap(@RequestBody Map map) {
        return iUserService.removeByMap(map);
    }

    /**
     * 根据Ids进行删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeByIds")
    public Object removeByIds(@RequestBody List<Integer> ids) {
        return iUserService.removeByIds(ids);
    }

    /**
     * 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
     *
     * @param user
     * @return
     */
    @PostMapping("/update1")
    public Object update1(@RequestBody User user) {
        return iUserService.update(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param user
     * @return
     */
    @PostMapping("/update2")
    public Object update2(@RequestBody User user) {
        return iUserService.update(user, new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }


    /**
     * 根据 ID 选择修改
     *
     * @param user
     * @return
     */
    @PostMapping("/updateById")
    public Object updateById(@RequestBody User user) {
        return iUserService.updateById(user);
    }

    /**
     * 根据ID批量更新
     *
     * @param user
     * @return
     */
    @PostMapping("/updateByIds1")
    public Object updateByIds1(@RequestBody List<User> user) {
        return iUserService.updateBatchById(user);
    }

    /**
     * 根据ID批量更新
     *
     * @param user
     * @return
     */
    @PostMapping("/updateByIds2")
    public Object updateByIds2(@RequestBody List<User> user) {
        return iUserService.updateBatchById(user, user.size());
    }

    /**
     * 根据 ID 查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public Object getById(@RequestParam int id) {
        return iUserService.getById(id);
    }

    /**
     * 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
     *
     * @return
     */
    @GetMapping("/getOne1")
    public Object getOne1() {
        return iUserService.getOne(new Wrapper<User>() {
            @Override
            public String getSqlSegment() {
                return null;
            }

            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }
        });
    }

    /**
     *  根据 Wrapper，查询一条记录
     *
     * @return
     */
    @GetMapping("/getOne2")
    public Object getOne2() {
        return iUserService.getOne(new Wrapper<User>() {
            @Override
            public String getSqlSegment() {
                return null;
            }

            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }
        }, true);
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @return
     */
    @GetMapping("/getMap")
    public Object getMap() {
        return iUserService.getMap(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @return
     */
    @GetMapping("/getObj")
    public Object getObj(){
        return iUserService.getObj(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        }, new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        });
    }

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("/getAll1")
    public Object getAll1() {
        return iUserService.list();
    }

    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping("/getAll2")
    public Object getAll2() {
        return iUserService.list(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

    /**
     * 查询（根据ID 批量查询）
     *
     * @param ids
     * @return
     */
    @PostMapping("/listByIds")
    public Object listByIds(@RequestBody List<Integer> ids) {
        return iUserService.listByIds(ids);
    }

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param map
     * @return
     */
    @PostMapping("/listByMap")
    public Object listByMap(@RequestBody Map map) {
        return iUserService.listByMap(map);
    }

    /**
     * 查询所有列表
     *
     * @return
     */
    @GetMapping("/getMap1")
    public Object listMaps1(){
        return iUserService.listMaps();
    }

    /**
     * 查询列表
     *
     * @return
     */
    @GetMapping("/getMap2")
    public Object listMaps2(){
        return iUserService.listMaps(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }

    /**
     * 查询全部记录
     *
     * @return
     */
    @GetMapping("/getAllRecords")
    public Object getAllRecords() {
        return iUserService.listObjs();
    }

    /**
     * 无条件分页查询
     *
     * @return
     */
    @GetMapping("/page")
    public Object page() {
        return iUserService.page(new IPage<User>() {
            @Override
            public List<OrderItem> orders() {
                return null;
            }

            @Override
            public List<User> getRecords() {
                return null;
            }

            @Override
            public IPage<User> setRecords(List<User> records) {
                return null;
            }

            @Override
            public long getTotal() {
                return 0;
            }

            @Override
            public IPage<User> setTotal(long total) {
                return null;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public IPage<User> setSize(long size) {
                return null;
            }

            @Override
            public long getCurrent() {
                return 0;
            }

            @Override
            public IPage<User> setCurrent(long current) {
                return null;
            }
        });
    }

    /**
     * 查询总记录数
     *
     * @return
     */
    @GetMapping("/count1")
    public Object count1() {
        return iUserService.count();
    }

    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @return
     */
    @GetMapping("/count2")
    public Object count2() {
        return iUserService.count(new Wrapper<User>() {
            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public MergeSegments getExpression() {
                return null;
            }

            @Override
            public void clear() {

            }

            @Override
            public String getSqlSegment() {
                return null;
            }
        });
    }






}
