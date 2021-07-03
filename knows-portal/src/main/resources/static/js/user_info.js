let userApp=new Vue({
    el:"#userApp",
    data:{
        user:{}
    },
    methods:{
        loadCurrentUser:function(){
            axios({
                url:"/v1/users/me",
                method:"get"
            }).then(function(response){
                //将axios获得的返回值绑定给vue的user属性
                userApp.user=response.data;
            })
        }
    },
    created:function(){
        this.loadCurrentUser();
    }
})