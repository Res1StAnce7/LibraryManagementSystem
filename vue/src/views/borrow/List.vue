<template>
  <div>
    <!--    搜索表单-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Input the book name" v-model="params.bookName"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Input the book code" v-model="params.bookNo"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Input the user name" v-model="params.userName"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Reset</el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id"  default-expand-all>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="bookName" label="Book Name"></el-table-column>
      <el-table-column prop="bookNo" label="Book Code"></el-table-column>
      <el-table-column prop="userNo" label="User ID"></el-table-column>
      <el-table-column prop="userName" label="User Name"></el-table-column>
      <el-table-column prop="userPhone" label="User Contact Information"></el-table-column>
      <el-table-column prop="score" label="Score Needed"></el-table-column>
      <el-table-column prop="createTime" label="Time of Days Book Lent"></el-table-column>
      <el-table-column prop="status" label="Borrowing Status"></el-table-column>
      <el-table-column prop="days" label="Number of Days Book Lent"></el-table-column>
      <el-table-column prop="returnDate" label="Date of Returning"></el-table-column>
      <el-table-column prop="note" label="Expiration Notification">
        <template v-slot="scope">
          <el-tag type="success" v-if="scope.row.note === 'Normal'">{{ scope.row.note }}</el-tag>
          <el-tag type="primary" v-if="scope.row.note === 'About to Expire'">{{ scope.row.note }}</el-tag>
          <el-tag type="warning" v-if="scope.row.note === 'Expired'">{{ scope.row.note }}</el-tag>
          <el-tag type="danger" v-if="scope.row.note === 'Expired'">{{ scope.row.note }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Management">
        <template v-slot="scope">
          <el-button type="primary" @click="returnBooks(scope.row)" v-if="scope.row.status === 'Lent'">Return Book</el-button>
        </template>
      </el-table-column>
      <el-table-column label="Operation">
        <template v-slot="scope">
          <el-popconfirm
              style="margin-left: 5px"
              title="Are you sure to delete this record?"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">Delete</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--    Subpage-->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie'

export default {
  name: 'BorrowList',
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: '',
        bookNo: ''
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/borrow/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        bookName: '',
        bookNo: '',
        userName: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/borrow/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('Delete successfully')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    returnBooks(row) {
      request.post("/borrow/saveReturn", row).then(res => {
        if (res.code === '200') {
          this.$notify.success('Return successfully')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>