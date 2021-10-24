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
    setIndexPage()
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

//현재 카테고리를 담는 전역변수
let nowCategory = "all"
let request_category = $('#request_category')
request_category.hide()

// index 페이지 처음 실행 화면 , 구독 서비스들 모두 나옴
function setIndexPage() {
    let subs = $('#subs')
    let main_logo = $('#main_logo')
    let selected_sub = $('#btn_all')

    $.ajax({
        type: "POST",
        url: "/api/index",
        contentType: 'application/json',
        data: JSON.stringify( {
            "category" : "all"
        }),
        success: function (response) {
            if (response['result'] === 'success') {
                nowCategory = "all"
                let content = $.parseJSON(response['subs'])
                console.log(content)
                subs.empty()
                main_logo.attr("src","../assets/img/index/index_main.png")
                selected_sub.css("color","red")
                $.each(content, function (index, item)
                {
                    subs.append(`
                        <div class="col-md-6 col-lg-4 mb-5">
                            <div class="portfolio-item mx-auto" data-bs-toggle="modal"
                                 data-bs-target="#portfolioModal1">
                                <div
                                    class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                    <div class="portfolio-item-caption-content text-center text-white"><i
                                        class="fas fa-plus fa-3x"></i></div>
                                </div>
                                <img class="img-fluid "  src="../assets/img/subs/${item.subscribe_name}.png"
                                     alt="..."/> <!-- 사진 크기 900x650 으로 해야함 -->
                                <span class="sub-title">${item.subscribe_name}</span>
                            </div>
                        </div>
                    `)
                })

            }

        }
    })
}

// 카테고리 선택시 구독 서비스들 나오게 하는 함수
function selectIndexPage(category) {
    let subs = $('#subs')
    let main_logo = $('#main_logo')

    $.ajax({
        type: "POST",
        url: "/api/SelectIndex",
        contentType: 'application/json',
        data: JSON.stringify( {
            "category" : category
        }),
        success: function (response) {
            if (response['result'] === 'success') {
                nowCategory = category
                let content = $.parseJSON(response['subs'])
                console.log(content)
                subs.empty()
                main_logo.attr("src","../assets/img/index/index_"+category+".png")

                $.each(content, function (index, item)
                {
                    subs.append(`
                        <div class="col-md-6 col-lg-4 mb-5">
                            <div class="portfolio-item mx-auto" data-bs-toggle="modal"
                                 data-bs-target="#portfolioModal1">
                                <div
                                    class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                    <div class="portfolio-item-caption-content text-center text-white"><i
                                        class="fas fa-plus fa-3x"></i></div>
                                </div>
                                <img class="img-fluid "  src="../assets/img/subs/${item.subscribe_name}.png"
                                     alt="..."/> <!-- 사진 크기 900x650 으로 해야함 -->
                                <span class="sub-title">${item.subscribe_name}</span>
                            </div>
                        </div>
                    `)
                })

            }

        }
    })
}

// 구독 이미지 클릭시 상세 페이지로 이동
function ContentToDetail(subscribe_name) {
}


function ToRecommand()
{
    request_category.attr("value",nowCategory)
}