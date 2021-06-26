<%--
  Created by IntelliJ IDEA.
  User: HSOD2
  Date: 2021-01-25
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 网站尾部 --%>
<div style="background-color: #f3f5f7;box-shadow:0px 0px 1px 1px #cccccc;">
    <div class="container">
        <div class="pt-5">
            <a href="#" class="text-secondary">帮助中心</a>
            <a href="#" class="text-secondary ml-3">联系我们</a>
            <a href="#" class="text-secondary ml-3">关于我们</a>
            <a href="#" class="text-secondary ml-3">客服QQ：12345</a>
        </div>
        <br>
        <div class="pb-5">
            <a href="#" class="text-secondary">官方粉丝群：678910</a>
            <a href="#" class="text-secondary ml-3">Copyright © 2019 上海XXXXXXX公司</a>
            <a href="#" class="text-secondary ml-3">Java课堂</a>
            <a href="#" class="text-secondary ml-3">沪ICP备xxxx</a>
        </div>


    </div>
</div>

<script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
<!-- bootstrap框架 -->
<%--<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"--%>
<%--        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"--%>
<%--        crossorigin="anonymous"></script>--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
        crossorigin="anonymous"></script>


<!-- 登录 -->
<div class="modal fade " id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">登录</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/login" method="post" onsubmit="return loginSubmit()">
                <div class="modal-body">

                    <div class="form-group">
                        <label for="loginInputEmail">邮箱</label>
                        <input name="email" type="email" class="form-control" id="loginInputEmail"
                               aria-describedby="emailHelpLon">
                        <small id="emailHelpLogin" class="form-text text-muted"></small>
                    </div>
                    <div class="form-group">
                        <label for="loginInputPwd">密码</label>
                        <input name="password" type="password" class="form-control" id="loginInputPwd">
                    </div>
                    <div class="form-group form-check">
                        <input name="autoLogin" type="checkbox" class="form-check-input" id="exampleCheck1" value="1">
                        <label class="form-check-label" for="exampleCheck1">自动登录</label>

                        <a href="/forgetPage" class="float-right">忘记密码</a>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">登录</button>
                </div>
            </form>

            <a href="#" class="ml-3 mb-3" data-toggle="modal" data-dismiss="modal"
               data-target="#registModal">还没有账号？点我注册</a>
        </div>

    </div>
</div>


<!-- 注册 -->
<div class="modal fade " id="registModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="RegistLabel">注册</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="/regist" method="post" onsubmit="return registSubmit()">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="RegEmailInput">邮箱</label>
                        <%-- 常用正则表达式 https://c.runoob.com/front-end/854   --%>
                        <input name="email" type="email" class="form-control" id="RegEmailInput"
                               onblur="checkEmail(this)"
                               aria-describedby="emailHelpReg" required>
                        <small id="emailHelpReg" class="form-text text-muted"></small>
                    </div>
                    <div class="form-group">
                        <label for="RegPasswordInput">密码</label>
                        <input name="password" type="password" class="form-control" id="RegPasswordInput" required
                               onblur="checkPassword(this)">
                        <small id="pwdHelpReg" class="form-text text-muted"></small>
                    </div>
                    <div class="form-group">
                        <label for="RegvcodeInput">验证码</label>
                        <div class="row">
                            <input name="vcode" type="vcode" class="form-control col-md-6 ml-3" id="RegvcodeInput"
                                   maxlength="4">
                            <img src="/vcode" class="col-md-4" onclick="changeVcode(this)">
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">注册</button>
                </div>
            </form>
            <a href="#" class="ml-3 mb-3" data-toggle="modal" data-dismiss="modal"
               data-target="#loginModal">已有账号？点我登录</a>

        </div>
    </div>
</div>

<%-- 前段JS脚本--%>
<script type="application/javascript">

    function loginSubmit() {

        var submitFlag = false;
        var email = $("#loginInputEmail").val();
        var pwd = $("#loginInputPwd").val();

        $.ajax({
            url: "/checkLogin",
            type: "POST",
            data: {email: email, password: pwd},
            async: false,
            success: function (result) {
                // 正确 直接登录提交表单
                if (result.code == 1) {
                    submitFlag = true;
                } else {
                    $("#emailHelpLogin").text("账号密码不正确");
                    $("#emailHelpLogin").attr("class", "invalid-feedback");
                    $("#emailHelpLogin").css("display","block");
                    submitFlag = false;
                }
                // 不正确 提示错误，不提交表单

            }
        });

        return submitFlag;


    }

    function changeVcode(imgNode) {
        imgNode.src = "/vcode?ram=" + new Date().getTime();
    }

    var registEmailFlag = false;
    var registPwdFlag = false;

    function registSubmit() {
        if (registEmailFlag && registPwdFlag) {
            return true;
        }
        return false;
    }

    function checkPassword(pwdNode) {
        var pwd = pwdNode.value;
        var pwdLen = pwd.length;
        if (pwdLen < 6) {
            $("#RegPasswordInput").removeClass("is-valid");
            $("#RegPasswordInput").addClass("is-invalid");
            $("#pwdHelpReg").text("密码不小于6位");
            $("#pwdHelpReg").attr("class", "invalid-feedback");
            registPwdFlag = false;
        } else {
            $("#RegPasswordInput").removeClass("is-invalid");
            $("#pwdHelpReg").attr("class", "valid-feedback");
            $("#RegPasswordInput").addClass("is-valid");
            $("#pwdHelpReg").text("");
            registPwdFlag = true;
        }

    }

    // 验证用户名是否存在 AJAX
    function checkEmail(emailNode) {
        var email = emailNode.value;

        // 验证邮箱格式
        var patt = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        var suc = patt.test(email);
        if (!suc) {
            // 不正确
            $("#RegEmailInput").removeClass("is-valid");
            $("#RegEmailInput").addClass("is-invalid");
            $("#emailHelpReg").text("email格式不正确");
            $("#emailHelpReg").attr("class", "invalid-feedback");
            registEmailFlag = false;
            return;
        }
        $("#RegEmailInput").removeClass("is-invalid");
        $("#emailHelpReg").attr("class", "valid-feedback");
        $("#RegEmailInput").addClass("is-valid");
        $("#emailHelpReg").text("");

        $.ajax({
            url: "/checkEmail?email=" + email,
            success: function (result) {
                // console.log(result.code);
                // console.log(result.message);
                // console.log(result.data);
                if (result.code == 1) {
                    console.log("ok");
                    registEmailFlag = true;
                    // 可以注册
                    // $("#RegEmailInput").removeClass("is-invalid");
                    // $("#emailHelpReg").attr("class", "valid-feedback");
                    // $("#RegEmailInput").addClass("is-valid");
                    // $("#emailHelpReg").text("");
                } else {
                    // 不可以注册
                    $("#RegEmailInput").removeClass("is-valid");
                    $("#RegEmailInput").addClass("is-invalid");
                    $("#emailHelpReg").text("email已经注册");
                    $("#emailHelpReg").attr("class", "invalid-feedback");
                    registEmailFlag = false;
                }

            }
        });

    }
</script>