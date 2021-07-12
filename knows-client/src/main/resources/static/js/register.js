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
                url:'http://localhost:9000/v1/users/register',
                data:form
            })
                .then(function(response) {
                    console.log("|"+response.status+"|"+OK+"|");
                    if(response.data=="注册完成"){
                        app.hasError = false;
                        location.href = '/login.html?register';
                    }else{
                        console.log(response.data);
                        app.hasError = true;
                        app.message = response.data;
                    }
                });
        }
    }
});