let searchApp=new Vue({
    el:"#searchApp",
    data:{
        key:""
    },
    methods:{
        search:function () {
            //点击搜索按钮跳转到search.html
            //encodeURI(key)是防中文乱码的设置
            location.href="/search.html?key="+encodeURI(this.key);
        }
    }
})