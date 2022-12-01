<template>
  <div>
    <!--    Search List-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="Input the Book Name" v-model="params.name"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="Input the Book Code" v-model="params.bookNo"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i> Search</el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i> Reset</el-button>
    </div>

    <el-table :data="tableData" stripe row-key="id" empty-text=" " default-expand-all>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="Book Name"></el-table-column>
        <el-table-column prop="bookNo" label="Book Code"></el-table-column>
        <el-table-column prop="description" width="200" label="Description"></el-table-column>
        <el-table-column prop="publishDate" label="Date of Publication"></el-table-column>
        <el-table-column prop="author" label="Author"></el-table-column>
        <el-table-column prop="publisher" label="Publisher"></el-table-column>
        <el-table-column prop="category" label="Category"></el-table-column>
        <el-table-column prop="score" label="Point"></el-table-column>
        <el-table-column prop="nums" label="Quantity"></el-table-column>
        <el-table-column prop="cover" label="Cover">
          <template v-slot="scope">
            <el-image :src="scope.row.cover" :preview-src-list="[scope.row.cover]"></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="createtime" label="Date of Creation"></el-table-column>
        <el-table-column prop="updatetime" label="Date of Update"></el-table-column>
        <el-table-column label="Operation" width="140">
          <template v-slot="scope">
            <el-button type="primary" @click="$router.push('/editBook?id=' + scope.row.id)">Edit</el-button>
            <el-popconfirm
               style="margin-left: 5px"
               title="Are you sure to delete this book?"
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
  name: 'BookList',
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
      request.get('/book/page', {
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
        name: '',
        bookNo: ''
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/book/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('Delete Success')
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
.className :empty::before {
  content: "";
}
</style>