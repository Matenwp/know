let questionApp =new Vue({
    el:"#questionApp",
    data:{
        question:{}
    },
    methods:{
        loadQuestion:function(){
            //获得浏览器地址栏中?之后的id
            //location.search是js提供的方法
            //功能是获得url中?之后的内容
            //注意如果url中有?并且?后有内容,就会获得,获得的内容是包含?的
            let qid=location.search;//?153
            console.log(qid);
            //如果qid不存在,证明url路径中没有?或?后没有内容
            if(!qid){
                alert("必须指定问题id");
                return;
            }
            // "?153"  ->   "153"
            qid=qid.substring(1);
            axios({
                //  /v1/questions/153
                url:"/v1/questions/"+qid,
                method:"get"
            }).then(function(response){
                questionApp.question=response.data;
                questionApp.updateDuration();
            })
        },
        updateDuration:function(){
            //创建问题时候的时间毫秒数
            let createtime = new Date(this.question.createtime).getTime();
            //当前时间毫秒数
            let now = new Date().getTime();
            let duration = now - createtime;
            if (duration < 1000*60){ //一分钟以内
                this.question.duration = "刚刚";
            }else if(duration < 1000*60*60){ //一小时以内
                this.question.duration =
                    (duration/1000/60).toFixed(0)+"分钟以前";
            }else if (duration < 1000*60*60*24){
                this.question.duration =
                    (duration/1000/60/60).toFixed(0)+"小时以前";
            }else {
                this.question.duration =
                    (duration/1000/60/60/24).toFixed(0)+"天以前";
            }
        }
    },
    created:function(){
        this.loadQuestion();
    }
})

let postAnswerApp=new Vue({
    el:"#postAnswerApp",
    data:{
        message:"",
        hasError:false
    },
    methods: {
        postAnswer:function(){
            //判断url上有id
            let qid=location.search;
            if(!qid){
                this.message="必须指定问题id";
                this.hasError=true;
                return;
            }
            //去掉?
            qid=qid.substring(1);
            //判断富文本编辑器中有内容
            let content=$("#summernote").val();
            if(!content){
                this.message="回答内容不能为空";
                this.hasError=true;
                return;
            }
            //定义form表单对象
            let form=new FormData();
            form.append("questionId",qid);
            form.append("content",content);
            //发送axios请求
            axios({
                url:"/v1/answers",
                method:"post",
                data:form
            }).then(function(response){
                console.log(response.data);
            })
        }

    }

})









