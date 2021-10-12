// Jquery Import
src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"
src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"

// 회원가입 
function register() {
    console.log('hello')
    let id_give = $('#userid').val()
    if (id_give.trim() == '') {
        alert('아이디를 제대로 입력해주세요')
    }

    let pw_give = $('#userpw').val()
    if (pw_give.trim() == '') {
        alert('비밀번호를 제대로 입력해주세요')
    }

    let email_give = $('#email').val()
    if (email_give.trim() == '') {
        alert('이메일을 제대로 입력해주세요')
    }
    console.log(id_give,pw_give,email_give)
        $.ajax({
            type: "POST",
            url: "api/register",
            contentType: 'application/json',
            data: JSON.stringify( {
                user_id: id_give,
                user_pw: pw_give,
                user_email: email_give
            }),
            success: function (response) {
                if (response['result'] == 'success') {
                    alert('회원가입이 완료되었습니다.')
                    window.location.href = '/login'
                } else {
                    alert(response['msg'])
                }
            }
        })

}