<template>
  <div>
    <div style="height: 60px; line-height: 60px; background-color: white; margin-bottom: 2px; display: flex">
      <div style="width: 300px">
        <img src="@/assets/logo.png" alt="" style="width: 40px; position: relative; top: 10px; left: 20px">
        <span style="margin-left: 25px; font-size: 24px">Book Management System</span>
      </div>
      <div style="flex: 1; text-align: right; padding-right: 20px">
        <el-dropdown size="medium">
          <span class="el-dropdown-link" style="cursor: pointer">
            {{ admin.username }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown" style="margin-top: -5px">
            <el-dropdown-item><div style="width: 50px; text-align: center;" @click="logout">Logout</div></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- side bar & main body -->
    <div style="display: flex">
      <!-- side bar navigation -->
      <div style="width: 200px; min-height: calc(100vh - 62px); overflow: hidden; margin-right: 2px; background-color: white">
        <el-menu :default-active="$route.path" router class="el-menu-demo" style="margin-bottom: 10px">
          <el-menu-item index="/">
            <i class="el-icon-element"></i>
            <span>Main</span>
          </el-menu-item>
          <el-submenu index="user">
            <template slot="title">
              <i class="el-icon-question"></i>
              <span>Member Management</span>
            </template>
            <el-menu-item index="/addUser">Add Member</el-menu-item>
            <el-menu-item index="/userList">Member List</el-menu-item>
          </el-submenu>
          <el-submenu index="admin">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>Admin Management</span>
            </template>
            <el-menu-item index="/addAdmin">Add Admin</el-menu-item>
            <el-menu-item index="/adminList">Admin List</el-menu-item>
          </el-submenu>
          <el-submenu index="category">
            <template slot="title">
              <i class="el-icon-s-operation"></i>
              <span>Book Category Management</span>
            </template>
            <el-menu-item index="/addCategory">Add Book Category</el-menu-item>
            <el-menu-item index="/categoryList">Book Category List</el-menu-item>
          </el-submenu>
          <el-submenu index="book">
            <template slot="title">
              <i class="el-icon-notebook-1"></i>
              <span>Book Management</span>
            </template>
            <el-menu-item index="/addBook">Add Book</el-menu-item>
            <el-menu-item index="/bookList">Book List</el-menu-item>
          </el-submenu>
          <el-submenu index="borrow">
            <template slot="title">
              <i class="el-icon-document-copy"></i>
              <span>Book Borrowing Management</span>
            </template>
            <el-menu-item index="/addBorrow">Add Borrowing </el-menu-item>
            <el-menu-item index="/borrowList">Borrowing List</el-menu-item>
          </el-submenu>
          <el-submenu index="return">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>Book Return Management</span>
            </template>
            <el-menu-item index="/returnList">Return List</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>

      <!-- Main body-->
      <div style="flex: 1; width: 0; background-color: white; padding: 10px">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from 'js-cookie'

export default {
  name: "Layout.vue",
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {}
    }
  },
  methods: {
    logout() {
      // Clear cookies
      Cookies.remove('admin')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>

</style>