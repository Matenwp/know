let tagsApp = new Vue({
    el:'#tagsApp',
    data:{
        tags:[]
    },
    methods:{
        loadTags:function () {
            console.log('执行了 loadTags');
            axios({
                url:'/v1/tags',
                method:'GET'
            }).then(function(r){
                if(r.status==OK){
                    tagsApp.tags=r.data;
                }
            })
        }
    },
    //created指定的方法会在当前页面所有内容加载完毕之后自动运行
    created:function () {
        this.loadTags();
    }
});