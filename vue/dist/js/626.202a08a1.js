"use strict";(self["webpackChunkvue"]=self["webpackChunkvue"]||[]).push([[626],{1626:function(e,t,o){o.r(t),o.d(t,{default:function(){return u}});var r=function(){var e=this,t=e._self._c;return t("div",{staticStyle:{width:"80%"}},[t("div",{staticStyle:{"margin-bottom":"30px"}},[e._v("Edit Book")]),t("el-form",{ref:"ruleForm",attrs:{inline:!0,rules:e.rules,model:e.form,"label-width":"100px"}},[t("el-form-item",{attrs:{label:"name",prop:"name"}},[t("el-input",{attrs:{placeholder:"Name"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),t("el-form-item",{attrs:{label:"Description",prop:"description"}},[t("el-input",{staticStyle:{width:"400px"},attrs:{type:"textarea",placeholder:"Description"},model:{value:e.form.description,callback:function(t){e.$set(e.form,"description",t)},expression:"form.description"}})],1),t("el-form-item",{attrs:{label:"Date of Publish",prop:"publishDate"}},[t("el-date-picker",{attrs:{type:"date","value-format":"yyyy-MM-dd",placeholder:"Select date of publish"},model:{value:e.form.publishDate,callback:function(t){e.$set(e.form,"publishDate",t)},expression:"form.publishDate"}})],1),t("el-form-item",{attrs:{label:"Author",prop:"author"}},[t("el-input",{attrs:{placeholder:"Author"},model:{value:e.form.author,callback:function(t){e.$set(e.form,"author",t)},expression:"form.author"}})],1),t("el-form-item",{attrs:{label:"Publisher",prop:"publisher"}},[t("el-input",{attrs:{placeholder:"Publisher"},model:{value:e.form.publisher,callback:function(t){e.$set(e.form,"publisher",t)},expression:"form.publisher"}})],1),t("el-form-item",{attrs:{label:"Category",prop:"category"}},[t("el-cascader",{staticStyle:{width:"220px"},attrs:{props:{value:"name",label:"name"},options:e.categories},model:{value:e.form.categories,callback:function(t){e.$set(e.form,"categories",t)},expression:"form.categories"}})],1),t("el-form-item",{attrs:{label:"Book Code",prop:"bookNo"}},[t("el-input",{attrs:{placeholder:"Input Book Code"},model:{value:e.form.bookNo,callback:function(t){e.$set(e.form,"bookNo",t)},expression:"form.bookNo"}})],1),t("el-form-item",{attrs:{label:"Point",prop:"cover"}},[t("el-input-number",{attrs:{min:10,max:30,label:"Point Needed"},model:{value:e.form.score,callback:function(t){e.$set(e.form,"score",t)},expression:"form.score"}})],1),t("br"),t("el-form-item",{attrs:{label:"Cover",prop:"cover"}},[t("el-upload",{staticClass:"avatar-uploader",attrs:{action:"http://localhost:9090/api/book/file/upload?token="+this.admin.token,"show-file-list":!1,"on-success":e.handleCoverSuccess}},[e.form.cover?t("img",{staticClass:"avatar",attrs:{src:e.form.cover,alt:""}}):t("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1)],1),t("div",{staticStyle:{"text-align":"center","margin-top":"30px"}},[t("el-button",{attrs:{type:"primary",size:"medium"},on:{click:e.save}},[e._v("Submit")])],1)],1)},a=[],s=(o(7658),o(4471)),l=o(680),i={name:"EditBook",data(){return{admin:l.Z.get("admin")?JSON.parse(l.Z.get("admin")):{},form:{score:10},categories:[],rules:{name:[{required:!0,message:"Required",trigger:"blur"}],bookNo:[{required:!0,message:"Required",trigger:"blur"}],score:[{required:!0,message:"Required",trigger:"blur"}]}}},created(){s.Z.get("/category/tree").then((e=>{this.categories=e.data}));const e=this.$route.query.id;s.Z.get("/book/"+e).then((e=>{this.form=e.data,this.form.category&&(this.form.categories=this.form.category.split(" > "),console.log(this.form.categories))}))},methods:{handleCoverSuccess(e){"200"===e.code&&(this.form.cover=e.data)},save(){s.Z.put("/book/update",this.form).then((e=>{"200"===e.code?(this.$notify.success("Update Success"),this.$router.push("/bookList")):this.$notify.error(e.msg)}))}}},c=i,m=o(1001),n=(0,m.Z)(c,r,a,!1,null,null,null),u=n.exports}}]);
//# sourceMappingURL=626.202a08a1.js.map