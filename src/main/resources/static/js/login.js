// Jquery Import
src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"


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
                    alert('로그인에 성공하였습니다!')
                    console.log(response['result'])
                    window.location.href = '/'
                } else {
                    alert('로그인에 실패했습니다!')
                }
            }
        })
    }
}