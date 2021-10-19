// Jquery Import
src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"
// 쿠키 사용을 위한 Jquery-cookie 임포트
src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"


// 로그인
function login() {
    let id_give = $('#userid').val()
    let pw_give = $('#userpw').val()

    if (id_give.trim() == '') {
        alert('아이디를 제대로 입력해주세요')
    }

    if (pw_give.trim() == '') {
        alert('비밀번호를 제대로 입력해주세요')

    } else {
        $.ajax({
            type: "POST",
            async : false,
            url: "/api/login",
            contentType: 'application/json',
            data: JSON.stringify( {
                user_id: id_give,
                user_pw: pw_give
            }),
            success: function (response) {
                if (response['result'] == 'success') {
                    alert('로그인에 성공하였습니다!');
                    $.cookie('loginToken', response['token']);
                    window.location.assign("/");
                } else {
                    alert('로그인에 실패했습니다!')
                }
            }
        })
    }
}