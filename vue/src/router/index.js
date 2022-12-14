import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/Login.vue'),
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/home/Home.vue'),
      },
      {
        path: 'userList',
        name: 'UserList',
        component: () => import('@/views/user/UserList.vue'),
      },
      {
        path: 'addUser',
        name: 'AddUser',
        component: () => import('@/views/user/AddUser.vue'),
      },
      {
        path: 'editUser',
        name: 'EditUser',
        component: () => import('@/views/user/EditUser.vue'),
      },
      {
        path: 'adminList',
        name: 'AdminList',
        component: () => import('@/views/admin/List.vue'),
      },
      {
        path: 'addAdmin',
        name: 'AddAdmin',
        component: () => import('@/views/admin/Add.vue'),
      },
      {
        path: 'editAdmin',
        name: 'EditAdmin',
        component: () => import('@/views/admin/Edit.vue'),
      },
      //  ====  Category  ====
      { path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/CategoryList.vue') },
      { path: 'addCategory', name: 'AddCategory', component: () => import('@/views/category/AddCategory.vue') },
      { path: 'editCategory', name: 'EditCategory', component: () => import('@/views/category/EditCategory.vue') },
      //  ====  Book  ====
      { path: 'bookList', name: 'BookList', component: () => import('@/views/book/List.vue') },
      { path: 'addBook', name: 'AddBook', component: () => import('@/views/book/Add.vue') },
      { path: 'editBook', name: 'EditBook', component: () => import('@/views/book/Edit.vue') },
      //  ====  Borrow  ====
      { path: 'borrowList', name: 'BorrowList', component: () => import('@/views/borrow/BorrowList.vue') },
      { path: 'addBorrow', name: 'AddBorrow', component: () => import('@/views/borrow/AddBorrow.vue') },
      { path: 'editBorrow', name: 'EditBorrow', component: () => import('@/views/borrow/EditBorrow.vue') },
      //  ====  Return  ====
      { path: 'returnList', name: 'returnList', component: () => import('@/views/return/List.vue') },
    ]
  },
  {
    path: "*",
    component:() => import('@/views/PageNotFound.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') next()
  const admin = Cookies.get("admin")
  if (!admin && to.path !== '/login') return next("/login")
  next()
})

export default router
