<template>
    <div class="content_right" :style="{ height: setting.screenHeight * 0.9 + 'px' }">
        <div id="tags-view-container" class="tags-view-container" v-if="dynamicTags.length > 0">
            <scroll-pane ref="scrollPane" class="tags-view-wrapper">

                <!-- <el-tag v-for="tag in dynamicTags" :key="tag.path" @close="handleClose(tag)" :closable="isActive(tag)"
                    :class="['tagItem', isActive(tag) ? '' : 'NoActive']"> -->
                <el-tag v-for="tag in dynamicTags" :key="tag.path" @close="handleClose(tag)" :closable="isActive(tag)"
                    :class="['tagItem']">
                <router-link :to="{ path: tag.path }">
                    {{ tag.title }}
                </router-link>
                </el-tag>
            </scroll-pane>
        </div>
        <div :style="{ height: setting.screenHeight - 86 + 'px' }" class="content_box">
            <router-view v-slot="{ Component }">
                <transition name="fade" mode="out-in">
                    <keep-alive :include="permission.needKeepAlive">
                        <component :is="Component" />
                    </keep-alive>
                </transition>
            </router-view>
        </div>
    </div>
</template>

<script setup>
import router from '@/router'
import store from '@/store'
import { computed } from 'vue'

const dynamicTags = computed(() => {
  return store.state.permission.dynamicTags
})
const setting = computed(() => {
  return store.state.setting
})
const permission = computed(() => {
  return store.state.permission
})

function isActive (tag) {
  return tag.path === router.currentRoute.value.path
}

function handleClose (tag) {
  store.commit('permission/delete_dynamicTags', tag)
}
</script>

<style lang="less" scoped>
.content_right {
    box-sizing: border-box;
}

.content_box {
    overflow: auto;
    padding: 10px;
    box-sizing: border-box;
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

.tags-view-container {
    padding: 5px;
    width: 100%;
    background: #fff;
    border-bottom: 1px solid #d8dce5;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);

    .tags-view-wrapper {
        .tags-view-item {
            display: inline-block;
            position: relative;
            cursor: pointer;
            height: 26px;
            line-height: 26px;
            border: 1px solid #d8dce5;
            color: #495060;
            background: #fff;
            padding: 0 8px;
            font-size: 12px;
            margin-left: 5px;
            margin-top: 4px;
            text-decoration: none;

            &:first-of-type {
                margin-left: 15px;
            }

            &:last-of-type {
                margin-right: 15px;
            }

            &.active {
                background-color: #42b983;
                color: #fff;
                border-color: #42b983;

                &::before {
                    content: '';
                    background: #fff;
                    display: inline-block;
                    width: 8px;
                    height: 8px;
                    border-radius: 50%;
                    position: relative;
                    margin-right: 2px;
                }
            }
        }
    }

    .contextmenu {
        margin: 0;
        background: #fff;
        z-index: 3000;
        position: absolute;
        list-style-type: none;
        padding: 5px 0;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 400;
        color: #333;
        box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);

        li {
            margin: 0;
            padding: 7px 16px;
            cursor: pointer;

            &:hover {
                background: #eee;
            }
        }
    }
}

.tagItem {
    margin-right: 5px;
    background-color: rgb(235.9, 245.3, 255);

    a {
        color: #409eff;
        text-decoration: none;
    }
}

.NoActive {
    background-color: #8d8d8d;
    color: #fff;

    a {
        text-decoration: none;
        color: #fff;
    }
}
</style>
