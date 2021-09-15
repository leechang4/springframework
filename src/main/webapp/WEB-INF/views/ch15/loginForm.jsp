<%@ page contentType="text/html; charset=UTF-8"%>

<!-- header에 taglib를 추가했으므로 이후의 jsp 파일들은 taglib를 중복으로 가져오지 않아도 됨 -->

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="card m-2">
    <div class="card-header">Login Form</div>
    <div class="card-body">
        <c:if test="${error != null}">
            <div class="alert alert-danger mb-2" role="alert">${error}</div>
        </c:if>
        
        <form method="post" action="login">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">mid</span>
                </div>
                <input type="text" name="mid" class="form-control" value="">
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">mpassword</span>
                </div>
                <input type="password" name="mpassword" class="form-control" value="">
            </div>
            <div class="mt-2">
                <input class="btn btn-info btn-sm" type="submit" value="로그인" />
                <input class="btn btn-info btn-sm" type="reset" value="다시작성" />
                <a class="btn btn-info btn-sm" href="content">취소</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>