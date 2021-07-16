package cn.tedu.knows.search;

import cn.tedu.knows.search.repository.ItemRepository;
import cn.tedu.knows.search.vo.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class KnowsSearchApplicationTests {

    @Resource
    ItemRepository itemRepository;
    @Test
    void contextLoads() {
        //新增一个Item到ES
        Item item=new Item()
                .setId(1l)
                .setTitle("罗技激光无线游戏鼠标")
                .setCategory("数码")
                .setBrand("罗技")
                .setPrice(120.0)
                .setImages("/image/1.jpg");
        itemRepository.save(item);
        System.out.println("ok");
    }

    //新增一批
    @Test
    void addList(){
        List<Item> items=new ArrayList<>();
        items.add(new Item(2L,"雷蛇机械游戏键盘","数码",
                "雷蛇",225.0,"/image/2.jpg"));
        items.add(new Item(3L,"微软办公静音键盘","数码",
                "微软",337.0,"/image/3.jpg"));
        items.add(new Item(4L,"索尼蓝牙游戏耳机","数码",
                "索尼",365.0,"/image/4.jpg"));
        items.add(new Item(5L,"罗技无线机械竞技键盘","数码",
                "罗技",125.0,"/image/5.jpg"));
        items.add(new Item(6L,"罗技有线激光鼠标","数码",
                "罗技",152.0,"/image/6.jpg"));
        //新增到es
        itemRepository.saveAll(items);
        System.out.println("ok");
    }

    @Test
    void getAll(){
        //全查
        Iterable<Item> items= itemRepository.findAll();
        //Iterable是List接口的父接口,可以遍历
        for(Item item:items){
            System.out.println(item);
        }
        System.out.println("-----------------------");
        Optional<Item> optional =itemRepository.findById(3l);
        System.out.println(optional.get());

        //删除
        //itemRepository.deleteById();
        //修改直接使用Save方法用相同id的覆盖掉


    }




}
