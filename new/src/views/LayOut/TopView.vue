<template>
  <div class="content_top" :style="{height:'50px'}">
    <div class="navbar" >

      <div class="right-menu">
        <div class="edit_my">
        <div class="one">
          <div style="margin-right: 10px;">
            {{ time.year }}

          </div>
          <div>

            {{ time.time }}
          </div>

        </div>
        <div class="two">
          {{ time.week }}
        </div>

        </div>
        <div class="avatar-container">
        <el-dropdown @command="handleCommand" class="right-menu-item hover-effect" trigger="click">
          <div class="avatar-wrapper">
            <img
             src="https://weparty-1252406287.cos.ap-chongqing.myqcloud.com/Weparty/club/18a0e26398db48b8adc49c4aa7e2c40aLOgT2hDsCK51aecea07c22a089da2daf0f41099a07ab.jpg"
              class="user-avatar" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item divided >
                <span @click="loginOut">退出登录</span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import store from '@/store'
import { ElMessage } from 'element-plus'
import { reactive } from 'vue'

const time = reactive({
  week: '',
  time: '',
  year: ''
})

function updateDateTime () {
  // 获取当前日期和时间
  const now = new Date()

  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0') // 月份从0开始
  const day = String(now.getDate()).padStart(2, '0')
  const formattedDate = `${year}/${month}/${day}`

  // 格式化时间（HH:MM:SS）
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  const seconds = String(now.getSeconds()).padStart(2, '0')
  const currentTime = `${hours}:${minutes}:${seconds}`

  // 获取星期几（中文）
  const weekdays = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
  const currentWeekday = weekdays[now.getDay()]

  time.week = currentWeekday
  time.time = currentTime
  time.year = formattedDate
}

const loginOut = () => {
  store.commit('user/login_out')
  store.commit('permission/change_userRouter', [])
  store.dispatch('permission/SET_ROUTER', '/')
  ElMessage.success({
    message: '退出成功'
  })
}

updateDateTime()
</script>

<style lang="less" scoped>
.content_top{
    width: 100%;

    box-sizing: border-box;
    .user{
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: flex-end;
      align-content: center;
      flex-wrap: wrap;
    }
}
.edit_my {
  display: flex;

  .one {
    display: flex;
    align-items: center;
    margin-right: 20px;
  }

  .two {
    margin-right: 20px;
  }
}

.navbar {
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    display: flex;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;
        display: flex;
        align-items: center;

        .user-avatar {
          cursor: pointer;
          width: 30px;
          height: 30px;
          border-radius: 50%;
          margin-right: 10px;
        }

        i {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
