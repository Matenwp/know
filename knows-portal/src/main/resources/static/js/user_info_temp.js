Vue.component("userapp",{
    props:["user"],
    template:`
    <div class="container-fluid font-weight-light">
        <div class="card">
          <h5 class="card-header"
              v-text="user.nickname">陈某</h5>
          <div class="card-body">
            <div class="list-inline mb-1 ">
                <a class="list-inline-item mx-3 my-1 text-center">
                  <div><strong>10</strong></div>
                  <div>回答</div>
                </a>
                <a class="list-inline-item mx-3 my-1 text-center" href="personal/myQuestion.html">
                  <div><strong v-text="user.questions">10</strong></div>
                  <div>提问</div>
                </a>
                <a class="list-inline-item mx-3 my-1 text-center" href="personal/collect.html">
                  <div><strong v-text="user.collections">10</strong></div>
                  <div>收藏</div>
                </a>
                <a class="list-inline-item mx-3 my-1 text-center" href="personal/task.html">
                  <div><strong>10</strong></div>
                  <div>任务</div>
                </a>
            </div>
          </div>
        </div>
      </div>
    `
})