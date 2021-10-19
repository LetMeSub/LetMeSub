// Jquery Import
src = "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"
// 쿠키 사용을 위한 Jquery-cookie 임포트
src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"


window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar 
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 72,
        });
    }
    ;

    // Collapse responsive navbar when toggler is visible
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });

});

// 메인화면 로드시 유저정보 확인
// 로그인 쿠키가 존재하면 로그인 상태
$(document).ready(function ()
{
    // user 정보
    setUserInfo()
});

function setUserInfo() {
    let userInfo = $('#user-info')
    let token = $.cookie('loginToken')
    console.log(token)

    $.ajax({
        type: "POST",
        url: "/index",
        contentType: 'application/json',
        data: JSON.stringify( {
            "login_token" : token
        }),
        success: function (response) {
            if (response['result'] === 'success') {
                userInfo.empty()
                userInfo.append(`
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded" >${token}님 환영 합니다.</a></li>
                            <li class="nav-item mx-0 mx-lg-1"><a class="nav-link py-3 px-0 px-lg-3 rounded" href="" onclick="logOut()">LogOut</a></li>                        </ul>
                    `)
            }

        }
    })


}

// 로그아웃 

function logOut()
{
    $.removeCookie('loginToken', {path: '/'})
    alert('로그아웃 되었습니다.')
    window.location.href = '/'
}