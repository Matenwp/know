let app = new Vue({
    el:'#app',
    data:{
        inviteCode:'',
        phone:'',
        nickname:'',
        password:'',
        confirm:'',
        message: '',
        hasError: false
    },
    methods:{
        register:function () {
            console.log('Submit');
            let form=new FormData();
            form.append("inviteCode",this.inviteCode);
            form.append("phone",this.phone);
            form.append("nickname",this.nickname);
            form.append("password",this.password);
            form.append("confirm",this.confirm);
            console.log(form);
            if(this.password != this.confirm){
                this.message = "确认密码不一致！";
                this.hasError = true;
                return;
            }
            axios({
                method:'post',
                url:'/register',
                data:form
            })
                .then(function(r) {
                    console.log("|"+r.status+"|"+OK+"|");
                    if(r.status == OK){
                        console.log("注册成功");
                        console.log(r.data);
                        app.hasError = false;
                        location.href = '/login.html?register';
                    }else{
                        console.log(r.data);
                        app.hasError = true;
                        app.message = r.data;
                    }
                });
        }
    }
});