<template>
  <div class="Content">
    <!-- <div class="Content_top">
      <el-switch v-model="setting.status" @change="changeStatus" />
    </div> -->
    <div class="Content_center"
      :style="{ width: setting.screenWidth * 0.4 + 'px', height: '300px' }">
      <div class="login">
        <div class="login_title">
          新闻管理系统
        </div>
        <el-form ref="ruleFormRef" class="login_form" :model="User" :rules="rules" label-width="auto" status-icon>
          <el-form-item label="账号" prop="username">
            <el-input v-model="User.username" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="User.password" />
          </el-form-item>
          <el-form-item>
            <div class="btns">
              <el-button type="success" @click="resetForm()">登录</el-button>
            </div>

          </el-form-item>
        </el-form>
      </div>
    </div>
    <div>
      <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
        <defs>
          <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
        </defs>
        <g class="parallax">
          <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7" />
          <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
          <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
          <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
        </g>
      </svg>
    </div>
  </div>
</template>

<script setup>
import store from '@/store'
import { computed, onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'

// 获得ref节点
const ruleFormRef = ref(null)
let resetForm
// 通过计算函数绑定store里的值
const setting = computed(() => {
  return store.state.setting
})

const rules = reactive({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})

const User = reactive({
  username: '',
  password: '',
  path: ''
})

onMounted(() => {
  // 执行里面的方法
  resetForm = () => {
    ruleFormRef.value.validate((valid, fields) => {
      if (valid) {
        login()
      } else {
        ElMessage.error({
          message: '请填写对应字段'
        })
      }
    })
  }

  let user = window.localStorage.getItem('user')
  if (user) {
    user = JSON.parse(user)
    User.username = user.username
    User.password = user.password
    setTimeout(() => {
      login()
    }, 1000)
  }
})

function login () {
  // 记录账号密码
  store.commit('user/change_userDetail', User)
  store.dispatch('user/login', User)
}

</script>

<style lang="less" scoped>
.Content {
  width: 100vw;
  height: 100vh;
  background-image: linear-gradient(antiquewhite, navajowhite);

  .Content_top {
    display: flex;
    justify-content: center;
    align-content: center;
    padding: 30px;
    box-sizing: border-box;
  }

  .Content_center {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border-radius: 20px;

    .login {
      padding: 20px;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;

      .login_title {
        text-align: center;
      }

      .login_form {
        margin-top: 40px;
        width: 60%;

        .btns {
          width: 100%;
          padding-top: 30px;
          display: flex;
          justify-content: space-around;
          align-content: center;
        }
      }
    }
  }
}
.waves {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 15vh;
    margin-bottom: -7px; /*Fix for safari gap*/
    min-height: 100px;
    max-height: 150px;
}

.content {
    position: relative;
    height: 20vh;
    text-align: center;
    background-color: white;
}

.content a {
    margin: 0 5px;
    font-size: 12px;
    color: #333;
}

.content a:hover {
    color: #000;
}

/* Animation */
.parallax > use {
    animation: move-forever 25s cubic-bezier(0.55, 0.5, 0.45, 0.5) infinite;
}
.parallax > use:nth-child(1) {
    animation-delay: -2s;
    animation-duration: 7s;
}
.parallax > use:nth-child(2) {
    animation-delay: -3s;
    animation-duration: 10s;
}
.parallax > use:nth-child(3) {
    animation-delay: -4s;
    animation-duration: 13s;
}
.parallax > use:nth-child(4) {
    animation-delay: -5s;
    animation-duration: 20s;
}
@keyframes move-forever {
    0% {
        transform: translate3d(-90px, 0, 0);
    }
    100% {
        transform: translate3d(85px, 0, 0);
    }
}
/*Shrinking for mobile*/
@media (max-width: 768px) {
    .waves {
        height: 40px;
        min-height: 40px;
    }
    .content {
        height: 30vh;
    }
    h1 {
        font-size: 24px;
    }
}
</style>
