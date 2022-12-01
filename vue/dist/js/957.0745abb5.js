"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[957],{1957:function(e,o,r){r.r(o),r.d(o,{default:function(){return u}});var t=function(){var e=this,o=e._self._c;return o("div",{staticStyle:{width:"80%"}},[o("div",{staticStyle:{"margin-bottom":"30px"}},[e._v("Borrow Record")]),o("el-form",{ref:"ruleForm",attrs:{inline:!0,rules:e.rules,model:e.form,"label-width":"100px"}},[o("el-form-item",{attrs:{label:"Book Code",prop:"bookNo"}},[o("el-select",{attrs:{clearable:"",filterable:"",placeholder:"Please select"},on:{change:e.selBook},model:{value:e.form.bookNo,callback:function(o){e.$set(e.form,"bookNo",o)},expression:"form.bookNo"}},e._l(e.books,(function(e){return o("el-option",{key:e.id,attrs:{label:e.bookNo,value:e.bookNo}})})),1)],1),o("el-form-item",{attrs:{label:"Book Name",prop:"bookName"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.bookName,callback:function(o){e.$set(e.form,"bookName",o)},expression:"form.bookName"}})],1),o("el-form-item",{attrs:{label:"Point Needed",prop:"score"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.score,callback:function(o){e.$set(e.form,"score",o)},expression:"form.score"}})],1),o("el-form-item",{attrs:{label:"Book Quantity",prop:"nums"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.nums,callback:function(o){e.$set(e.form,"nums",o)},expression:"form.nums"}})],1),o("br"),o("el-form-item",{attrs:{label:"User ID",prop:"userNo"}},[o("el-select",{attrs:{filterable:"",placeholder:"Please select"},on:{change:e.selUser},model:{value:e.form.userNo,callback:function(o){e.$set(e.form,"userNo",o)},expression:"form.userNo"}},e._l(e.users,(function(e){return o("el-option",{key:e.id,attrs:{label:e.username,value:e.username}})})),1)],1),o("el-form-item",{attrs:{label:"Username",prop:"userName"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.userName,callback:function(o){e.$set(e.form,"userName",o)},expression:"form.userName"}})],1),o("el-form-item",{attrs:{label:"User Phone Number",prop:"userPhone"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.userPhone,callback:function(o){e.$set(e.form,"userPhone",o)},expression:"form.userPhone"}})],1),o("el-form-item",{attrs:{label:"User Points",prop:"account"}},[o("el-input",{attrs:{disabled:""},model:{value:e.form.account,callback:function(o){e.$set(e.form,"account",o)},expression:"form.account"}})],1),o("el-form-item",{attrs:{label:"Number of Days Book Lent",prop:"days"}},[o("el-input-number",{attrs:{min:1,max:30,label:"Number of Days Book Lent"},model:{value:e.form.days,callback:function(o){e.$set(e.form,"days",o)},expression:"form.days"}})],1)],1),o("div",{staticStyle:{"text-align":"center","margin-top":"30px"}},[o("el-button",{attrs:{type:"primary",size:"medium"},on:{click:e.save}},[e._v("Submit")])],1)],1)},s=[],a=r(4471),l={name:"AddBook",data(){return{form:{days:1},books:[],users:[],rules:{bookNo:[{required:!0,message:"Book Code",trigger:"blur"}],userNo:[{required:!0,message:"Input user id",trigger:"blur"}]}}},created(){a.Z.get("/book/list").then((e=>{this.books=e.data})),a.Z.get("/user/list").then((e=>{this.users=e.data.filter((e=>e.status))}))},methods:{save(){this.$refs["ruleForm"].validate((e=>{e&&a.Z.post("/borrow/save",this.form).then((e=>{"200"===e.code?(this.$notify.success("Borrow successfully"),this.$refs["ruleForm"].resetFields()):this.$notify.error(e.msg)}))}))},selBook(){const e=this.books.find((e=>e.bookNo===this.form.bookNo));a.Z.get("/book/"+e.id).then((e=>{this.$set(this.form,"bookName",e.data.name),this.form.score=e.data.score,this.form.nums=e.data.nums}))},selUser(){const e=this.users.find((e=>e.username===this.form.userNo));a.Z.get("/user/"+e.id).then((e=>{this.$set(this.form,"userName",e.data.name),this.form.userPhone=e.data.phone,this.form.account=e.data.account}))}}},n=l,m=r(1001),i=(0,m.Z)(n,t,s,!1,null,null,null),u=i.exports}}]);
//# sourceMappingURL=957.0745abb5.js.map