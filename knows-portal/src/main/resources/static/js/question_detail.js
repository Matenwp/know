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
                addDuration(questionApp.question);
            })
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
                //新增成功之后的操作
                let answer=response.data;
                //我们需要将新增成功的answer对象
                // 添加在当前保存所有回答的列表中
                answersApp.answers.push(answer);
                //将当前富文本编辑器内容清空
                $("#summernote").summernote("reset");
                // 无论之前是否发生错误提示,在新增成功时,都将这个提示隐藏
                postAnswerApp.hasError=false;
                //设置当前回答的持续时间是:"刚刚"
                answer.duration="刚刚";
            })
        }
    }
})

// 查询显示所有回答的
let answersApp=new Vue({
    el:"#answersApp",
    data:{
        answers:[]
    },
    methods:{
        loadAnswers:function(){
            let qid=location.search;
            if(!qid){
                alert("必须指定问题id");
                return;
            }
            qid=qid.substring(1);
            axios({
                url:"/v1/answers/question/"+qid,
                method:"get",
            }).then(function(response){
                console.log(response.data);
                answersApp.answers=response.data;
                //下面是计算持续时间的方法调用
                let answers=answersApp.answers;
                for(let i=0;i<answers.length;i++){
                    addDuration(answers[i]);
                }
            })
        },
        postComment:function(answerId){
            console.log("新增评论对应的回答id:"+answerId);
            if(!answerId)
                return;
            //获得当前评论区域中的textarea
            let textarea=$("#addComment"+answerId+" textarea")
            let content=textarea.val();
            if(!content)
                return;
            let form=new FormData();
            form.append("answerId",answerId);
            form.append("content",content);
            axios({
                url:"/v1/comments",
                method:"post",
                data:form
            }).then(function(response){
                console.log(response.data);
                //清空新增的文字
                textarea.val("");
                //将弹出的输入框折叠隐藏
                $("#addComment"+answerId).collapse("hide");
                //实现新增的评论立即显示在页面上
                //原理是将新增的评论对象添加到对应的回答的评论数组中
                //定义一个变量保存我么新增成功的评论
                let comment=response.data;
                //定义一个变量引用所有回答
                let answers=answersApp.answers;
                // 遍历所有回答
                for(let i=0;i<answers.length;i++){
                    //判断当前answers[i]是不是本次新增评论的回答
                    if(answers[i].id == answerId){
                        //如果是,将本次新增的评论添加到当前回答的评论数组中
                        answers[i].comments.push(comment);
                        return;
                    }
                }
            })
        }
    },
    created:function(){
        this.loadAnswers();
    }
})









