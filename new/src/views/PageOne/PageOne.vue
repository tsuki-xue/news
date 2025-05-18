<template>
  <div class="content">
    <div class="content_top content_seach">
      <el-form :inline="true" :model="userForm" class="user-search" style="margin-top: 10px">
        <el-form-item>
          <el-input style="width: 220px;" v-model="userForm.name" placeholder="请输入新闻标题"></el-input>
        </el-form-item>

        <el-form-item>
         <el-select style="width: 220px;" v-model="userForm.type" placeholder="请选择新闻类型">
                      <el-option v-for="item in questionType" :key="item.id" :label="item.name"
                        :value="item.id"></el-option>
                    </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="activitySearch()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <div>
            <el-button type="success" @click="resetQuery">重置</el-button>
          </div>
        </el-form-item>
        <el-form-item v-if="userLeave == 1"><el-button type="success" @click="addNew">新增新闻</el-button></el-form-item>
        <el-form-item v-if="userLeave == 1"><el-button type="success"
            @click="addQuestionType">新增栏目</el-button></el-form-item>

      </el-form>
    </div>

    <div class="content_center">
      <el-table ref="grpNickNameTable" :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column align="center" prop="index" width="70" label="排序">
        </el-table-column>

        <el-table-column align="center" prop="title" width="160" show-overflow-tooltip label="新闻名称">
        </el-table-column>

        <el-table-column align="center" prop="categoryName" label="新闻栏目">
        </el-table-column>

        <el-table-column align="center" prop="content" show-overflow-tooltip label="新闻内容">

        </el-table-column>

        <el-table-column align="center" prop="author" label="新闻作者">
        </el-table-column>

        <el-table-column align="center" prop="source" label="新闻来源">
        </el-table-column>

        <el-table-column align="center" prop="lookNum" label="查看量">
        </el-table-column>

        <el-table-column align="center" prop="collectNum" label="收藏量">

        </el-table-column>

        <el-table-column align="center" prop="publishTime" show-overflow-tooltip label="更新时间">
        </el-table-column>

        <el-table-column align="center" label="状态">
          <template v-slot="scope">
            <div :style="{ color: scope.row.venucolor }">{{ scope.row.venuStatus }}</div>
          </template>
        </el-table-column>

        <el-table-column width="200px" align="center" fixed="right" label="操作">
          <template #default="scope">
            <div v-if="userLeave == 1">
              <el-button type="primary" size="small" @click.prevent="deleteRow(scope.$index, scope.row, 1)">
                编辑
              </el-button>
              <el-button type="primary" v-if="scope.row.status == 0" size="small"
                @click.prevent="deleteRow(scope.$index, scope.row, 2)">
                发布
              </el-button>
              <el-button type="danger" size="small" @click.prevent="deleteRow(scope.$index, scope.row, 3)">
                删除
              </el-button>
            </div>
            <div v-else>
              <el-button type="primary" size="small" @click.prevent="deleteRow(scope.$index, scope.row, 4)">
                查看
              </el-button>
              <el-button type="success" size="small" @click.prevent="deleteRow(scope.$index, scope.row, 5)">
                {{ scope.row.isCollect == 0 ? '收藏' : '取消收藏' }}
              </el-button>
            </div>

          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="content_bottom">
      <el-pagination v-model:current-page="page.pageNum" @current-change="pageChange" layout="prev, pager, next"
        :total="page.total" />
    </div>
    <el-dialog v-model="addAndLook.status" :title="addAndLook.type == 1 ? '编辑新闻' : '新增新闻'" width="800"
      :before-close="handleClose" :close-on-click-modal="false">

      <div class="add">
        <el-scrollbar :max-height="'60vh'">
          <el-form ref="ruleFormRef" :inline="true" :model="DetailForm" label-width="120px" style="margin-top: 10px"
            :rules="rules">

            <el-form-item style="width: 100%;" label="新闻名称" prop="name">
              <div class="add_item">
                <el-row>
                  <el-col :span="8">

                  </el-col>
                  <el-col :span="14">
                    <el-input v-model="DetailForm.title" maxlength="30" placeholder="请输入新闻名称"></el-input>
                  </el-col>
                  <el-col :span="2">

                  </el-col>
                </el-row>
              </div>
            </el-form-item>
            <el-form-item style="width: 100%;" label="新闻类型">
              <div class="add_item">
                <el-row>
                  <el-col :span="8">
                  </el-col>
                  <el-col :span="14">
                    <el-select v-model="DetailForm.categoryId" placeholder="请选择新闻类型">
                      <el-option v-for="item in questionType" :key="item.id" :label="item.name"
                        :value="item.id"></el-option>
                    </el-select>
                  </el-col>
                  <el-col :span="2">

                  </el-col>
                </el-row>
              </div>
            </el-form-item>
            <el-form-item style="width: 100%;" label="新闻内容" prop="address">
              <div class="add_item">
                <el-row>
                  <el-col :span="8">

                  </el-col>
                  <el-col :span="14">
                    <el-input type="textarea" autosize v-model="DetailForm.content" placeholder="请输入新闻内容"></el-input>
                  </el-col>
                  <el-col :span="2">

                  </el-col>
                </el-row>
              </div>
            </el-form-item>
            <el-form-item style="width: 100%;" label="新闻作者">
              <div class="add_item">
                <el-row>
                  <el-col :span="8">

                  </el-col>
                  <el-col :span="14">
                    <el-input v-model="DetailForm.author" maxlength="30"></el-input>
                  </el-col>
                  <el-col :span="2">

                  </el-col>
                </el-row>
              </div>
            </el-form-item>
            <el-form-item style="width: 100%;" label="新闻来源">
              <div class="add_item">
                <el-row>
                  <el-col :span="8">

                  </el-col>
                  <el-col :span="14">
                    <el-input v-model="DetailForm.source" maxlength="30"></el-input>
                  </el-col>
                  <el-col :span="2">

                  </el-col>
                </el-row>
              </div>
            </el-form-item>
          </el-form>
        </el-scrollbar>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="handleClose">返回</el-button>
          <el-button type="primary" @click="CheckAdd">
            确认
          </el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog title="新增栏目" v-model="addQuestionTypeStatus" width="40%" center :close-on-click-modal="false">
      <el-form :model="saveType" label-width="100px" class="demo-ruleForm" style="overflow: auto;margin-top: 20px;">
        <el-form-item label="新增类型：" width="100">
          <el-input v-model="saveType.typeTitle" style="width:50%"></el-input>
          <el-button type="primary" size="small" style="margin-left: 20px;" @click="SaveCompetitionType">确
            定</el-button>
        </el-form-item>
        <el-form-item label="已有栏目：" width="100">
          <el-table :data="questionType" style="max-height: 300px;">
            <el-table-column label="排序" align="center">
              <template #default="scope">
                {{ scope.row.index }}
              </template>
            </el-table-column>
            <el-table-column label="栏目名称" align="center" prop="name"></el-table-column>
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button type="primary" size="small" @click="editType(scope.row)">修改</el-button>
                <el-button type="primary" size="small" @click="deleteType(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog title="修改栏目" @close="closeEditShow" v-model="editItem.show" width="30%">
      <el-form>
        <el-form-item label="原栏目名称：" width="100">
          <el-input disabled v-model="editItem.oldName"></el-input>
        </el-form-item>
        <el-form-item label="现栏目名称：" width="100">
          <el-input v-model="editItem.nowName"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="checkEdit">确 定</el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="dialogVisible" :title="newsData.title" center width="60%" :before-close="closeIts"
      class="news-dialog">
      <div class="news-meta">
        <div class="meta-item">
          <span class="meta-label">作者：</span>
          <span class="meta-value">{{ newsData.author || '未知' }}</span>
        </div>
        <div class="meta-item">
          <span class="meta-label">来源：</span>
          <span class="meta-value">{{ newsData.source || '未知' }}</span>
        </div>
        <div class="meta-item">
          <span class="meta-label">发布时间：</span>
          <span class="meta-value">{{ newsData.publishTime }}</span>
        </div>
      </div>
      <el-divider />
      <div class="news-content">
        <p>
          {{newsData.content}}
        </p>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="closeIts">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  reactive
} from 'vue'

import api from '@/api/QuizWithPrizes/index'
import {
  ElMessage,
  ElMessageBox
} from 'element-plus'

import store from '@/store'

export default {
  name: 'PageOne',

  data () {
    return {
      saveType: {
        typeTitle: ''
      },
      content: '',
      bhdetial: {
        show: false,
        text: ''
      },
      dialogObj: {
        status: false,
        img: ''
      },
      // 上传相关
      headers: {
        methods: 'get',
        token: localStorage.getItem('token')
      },
      data: {
        fileId: '1',
        groupId: '1'
      },
      avater: '',
      avaterLoading: false,
      selectedFile: '',
      userPower: {
        add: true,
        edit: true,
        look: true,
        clear: true
      },
      rules: {
      },
      loading: false,
      userInfo: {},
      clubList: [],
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      tableData: [],
      // 用于用户选择后的页面回显的列表
      questionTableData: [],
      uurl: 'http://dl.yuanguiwen.com:8448/wePartyConsole/fileLoadURL',

      // 搜索项
      userForm: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        type: ''
      },
      // 记录分页
      page: {
        pageNum: 1,
        pageSize: 10,
        total: 1
      },
      // 新增与查看dialog
      addAndLook: {
        type: 0, // 0新增，1编辑
        status: false
      },
      lastPassword: '',
      // 角色列表
      roleList: reactive([]),

      // 添加的数据form
      DetailForm: {
        isLook: false,
        categoryId: '',
        title: '',
        content: '',
        author: '',
        source: ''
      },
      userFormRest: this.userForm,
      questionType: [],

      userDetail: {},
      isLook: false,
      ExcelData: '',
      Loading: '',
      dataList: [],
      dataList_sp: [],
      addQuestionTypeStatus: false,
      editItem: {
        show: false,
        data: {},
        oldName: '',
        nowName: ''
      },
      userLeave: 2, // 1为管理员 2为普通用户
      disabledList: {
        one: false,
        tow: false
      },
      newsData: {}
    }
  },
  methods: {
    deleteType (row) {
      ElMessageBox.alert('确定要删除该栏目吗', '提示', {
        confirmButtonText: '确认',
        callback: (action) => {
          if (action === 'confirm') {
            api.categoriesDelete({
              ...row
            }).then(res => {
              this.sendMessage(res)
              this.GetVenueType()
            })
          }
        }
      })
    },
    closeEditShow () {
      this.editItem.show = false
      this.editItem.nowName = ''
    },
    checkEdit () {
      if (this.editItem.nowName === '') {
        return this.errorMessage('请先输入栏目名称')
      }
      api.categoriesUpdate({
        ...this.editItem.data,
        name: this.editItem.nowName
      }).then(res => {
        this.sendMessage(res)
        this.GetVenueType()
        this.editItem.show = false
      })
    },
    editType (row) {
      this.editItem.data = row
      this.editItem.oldName = row.name

      this.editItem.show = true
    },
    addQuestionType () {
      this.addQuestionTypeStatus = true
    },
    async SaveCompetitionType () {
      const res = await api.categoriesSave({
        name: this.saveType.typeTitle,
        private: 'admin'
      })
      this.sendMessage(res)
      this.GetVenueType()
      this.saveType.typeTitle = ''
    },
    getRichText (e) {

    },
    resetDetail () {
      this.DetailForm = {
        isLook: false,
        categoryId: '',
        title: '',
        content: '',
        author: '',
        source: ''
      }
      this.avater = ''
    },
    openImg (url) {
      this.dialogObj.status = true
      this.dialogObj.img = url
    },
    closeAll () {
      this.dialogObj.status = false
      this.bhdetial.show = false
      this.dialogVisible = false
    },

    Checkbh () {
      api.withdrawAudit({
        logId: this.DetailForm.logId,
        status: 'N',
        remark: this.bhdetial.text
      }).then(res => {
        this.sendMessage(res)
        if (res.code === 200) {
          this.closeAll()
          this.addAndLook.status = false
          this.GetVenueType()
        }
      })
    },
    // 确认新增，查看,编辑
    CheckAdd (type) {
      if (this.addAndLook.type === 0) {
        api.newsSave(this.DetailForm).then(res => {
          if (res.code === 200) {
            this.GetVenueType()
            this.sendMessage(res)
            this.addAndLook.status = false
          }
        })
      } else
      if (this.addAndLook.type === 1) {
        api.newsUpdate(this.DetailForm).then(res => {
          if (res.code === 200) {
            this.GetVenueType()
            this.sendMessage(res)
            this.addAndLook.status = false
          }
        })
      }
    },
    sendMessage (res) {
      if (res.code === 200) {
        ElMessage.success({
          message: '操作成功'
        })
      } else {
        ElMessage.error({
          message: res.message
        })
      }
    },
    sendMessageBox (tip, title, callback, other) {
      ElMessageBox.alert(tip, title, {
        confirmButtonText: '确认',
        callback: (action) => {
          if (action === 'confirm') {
            callback()
          } else {
            other()
          }
        }
      })
    },

    // 打开添加试卷弹窗
    addNew () {
      this.addAndLook.type = 0
      this.resetDetail()
      this.addAndLook.status = true
    },

    // 重置表单
    resetQuery () {
      this.page.pageNum = 1
      this.userForm = {
        pageNum: 1,
        pageSize: 10,
        name: '',
        type: ''
      }
      this.GetVenueType()
    },
    // 重置输入框
    rediteDetaForm () {
      this.DetailForm = {
        isLook: false,
        categoryId: '',
        title: '',
        content: '',
        author: '',
        source: ''
      }
    },
    // 关闭新增与查看
    handleClose () {
      this.addAndLook.status = false
    },

    // 搜索
    activitySearch () {
      this.page.pageNum = 1
      let data = this.dataList.filter(item => {
        return item.title.indexOf(this.userForm.name) > -1
      })
      if (this.userForm.type !== '') {
        data = data.filter(item => {
          return item.categoryId === this.userForm.type
        })
      }
      this.dataList_sp = JSON.parse(JSON.stringify(data))

      this.computedList()
    },
    // 页数改变
    pageChange (e) {
      this.page.pageNum = e
      this.computedList()
    },
    // 点击详情
    deleteRow (index, row, type) {
      this.resetDetail()
      if (type === 1) {
        this.addAndLook.type = 1

        this.DetailForm = {
          ...this.DetailForm,
          ...row
        }
        this.addAndLook.status = true
      } else if (type === 2) {
        this.sendMessageBox('确定发布该新闻吗？', '提示', () => {
          api.newsUpdate({
            id: row.id,
            status: 1
          }).then(res => {
            this.sendMessage(res)
            this.GetVenueType()
          })
        })
      } else if (type === 3) {
        this.sendMessageBox('确定删除该新闻吗？', '提示', () => {
          api.newsDelete({
            id: row.id
          }).then(res => {
            this.sendMessage(res)
            this.GetVenueType()
          })
        })
      } else if (type === 4) {
        this.newsData = row
        api.newsAddView({
          id: row.id
        })
        this.dialogVisible = true
      } else if (type === 5) {
        const obj = {
          userName: store.state.user.userDetail,
          newsId: row.id
        }
        let Api

        if (row.isCollect === 0) {
          Api = api.collect(obj)
        } else {
          Api = api.collectCancel(obj)
        }

        Api.then(res => {
          if (res.code === 200) {
            this.GetVenueType()
            ElMessage.success({
              message: row.isCollect === 0 ? '收藏成功' : '取消收藏成功'
            })
          } else {
            ElMessage.error({
              message: res.message
            })
          }
        })
      }
    },
    // 获取列表数据
    GetAdminList () {
      this.loading = true
      const userForm = this.userForm
      userForm.pageNum = this.page.pageNum
      const request = api.newsList({
        auth: store.state.user.userDetail
      })
      request.then((res) => {
        if (res.code === 200) {
          let findIndex = -1
          this.dataList = res.data.map((item, index) => {
            item.index = index + 1
            findIndex = this.questionType.findIndex((items) => {
              return item.categoryId === items.id
            })
            if (findIndex === -1) {
              item.categoryName = '被删除的栏目'
            } else {
              item.categoryName = this.questionType[findIndex].name
            }

            if (item.status === 1) {
              item.venucolor = '#67C23A'
              item.venuStatus = '已发布'
            } else if (item.status === 0) {
              item.venucolor = '#E6A23C'
              item.venuStatus = '草稿'
            } else if (item.status === 2) {
              item.venucolor = '#F56C6C'
              item.venuStatus = '已下架'
            }

            return item
          })
          this.dataList_sp = JSON.parse(JSON.stringify(this.dataList))
          this.page.total = res.data.total
          this.computedList()
        } else {
          ElMessage.error({
            message: '请求失败'
          })
        }
        this.loading = false
      })
    },
    closeIts () {
      this.GetAdminList()
      this.dialogVisible = false
    },
    computedList () {
      const { pageNum, pageSize } = this.page
      const start = (pageNum - 1) * pageSize
      const end = pageNum * pageSize
      const listArr = this.dataList_sp.slice(start, end).map((item, index) => {
        item.index = start + index + 1
        item.sc = 0
        return item
      })
      this.tableData.splice(0, this.tableData.length)
      this.tableData.splice(0, 0, ...listArr)
      this.page.total = this.dataList_sp.length
    },
    getUsersGroup () {
      this.GetVenueType()
    },
    GetVenueType () {
      api.categoriesList().then(res => {
        if (res.code === 200) {
          this.questionType = res.data.map((item, index) => {
            return {
              ...item,
              index: index + 1
            }
          })
          this.GetAdminList()
        }
      })
    }

  },

  beforeMount () {
    this.getUsersGroup()
    this.userLeave = store.state.user.userLeavel
  }
}
</script>
<style lang="less" scoped>
::v-deep .el-pagination button {
  background-color: transparent;
}

::v-deep .el-pager li {
  background-color: transparent;
}

::v-deep .el-scrollbar__bar.is-horizontal {
  display: none;
}

.content {
  padding: 20px;
  box-sizing: border-box;

  .content_top {
    display: flex;
    justify-content: space-between;
    align-content: center;
    flex-wrap: wrap;
    padding-right: 50px;
  }

  .content_center {
    flex: 1;
    background-color: #fff;
    border-radius: 5px 5px 0 0;
  }

  .content_bottom {
    display: flex;
    justify-content: flex-end;
    padding-top: 10px;
    background-color: #fff;
    border-radius: 0 0 5px 5px;
  }
}

.flexs {
  display: flex;
  justify-content: space-between;
  align-content: center;
}

.dialog-footer {
  display: flex;
  justify-content: center;
}

.add {

  .add_item {
    width: 100%;

  }
}
.news-dialog {
  --el-dialog-padding-primary: 20px;
}

.news-meta {
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-wrap: wrap;
  gap: 16px;
  margin-bottom: 12px;
  color: #666;
  font-size: 14px;
}

.meta-item {
  display: flex;
  align-items: center;
}

.meta-label {
  font-weight: 500;
  color: #333;
}

.news-content {
  line-height: 1.8;
  color: #333;
  font-size: 15px;
  max-height: 60vh;
  overflow-y: auto;
  padding: 0 10px;
}

.news-content :deep(p) {
  margin: 0.8em 0;
}

.news-content :deep(img) {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 10px auto;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
