package cn.tedu.knows.search;

import cn.tedu.knows.search.repository.ItemRepository;
import cn.tedu.knows.search.vo.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    //查询1
    @Test
    void  find1(){
        Iterable<Item> items=itemRepository
                .queryItemsByTitleMatches("游戏鼠标");
        for(Item item: items){
            System.out.println(item);
        }
    }

    //查询2
    @Test
    void find2(){
        Iterable<Item> items=itemRepository
                .queryItemsByTitleMatchesAndBrandMatches(
                        "鼠标","罗技");
        for(Item item : items){
            System.out.println(item);
        }
    }

    //查询3:排序
    @Test
    void find3(){
        Iterable<Item> items=itemRepository
                .queryItemsByTitleMatchesOrderByPriceAsc("游戏");
        items.forEach(item -> System.out.println(item));

    }

    //分页
    @Test
    void page(){
        //Page是包含查询结果和分页信息的对象
        //PageRequest.of是一个方法,
        // 参数是分页查询的页码和每页条数,注意页码从0开始
        //Pageable是PageRequest.of方法的返回值类型
        Page<Item> page=itemRepository
                .queryItemsByTitleMatchesOrderByPriceAsc(
                        "游戏", PageRequest.of(0,2));
        for(Item item:page){
            System.out.println(item);
        }
        //page.forEach(item -> System.out.println(item));
        //输出分页信息
        System.out.println("总页数:"+page.getTotalPages());
        System.out.println("当前页:"+page.getNumber());
        System.out.println("每页条数:"+page.getSize());
        System.out.println("是不是首页:"+page.isFirst());
        System.out.println("是不是末页:"+page.isLast());
        System.out.println("下一页:"
                +page.nextOrLastPageable().getPageNumber());
        System.out.println("上一页:"
                +page.previousOrFirstPageable().getPageNumber());


    }


}
