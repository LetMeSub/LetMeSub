// Jquery Import
src = "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"
// 쿠키 사용을 위한 Jquery-cookie 임포트
src = "https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"


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
$(document).ready(function () {
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
        data: JSON.stringify({
            "login_token": token
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

function logOut() {
    let token = $.cookie('loginToken')
    $.ajax({
        type: "POST",
        url : "/api/logout",
        contentType: 'application/json',
        data: JSON.stringify({
            "login_token": token
        }),
        success: function (response) {
            if (response['result'] == 'success')
            {
                alert('로그아웃 되었습니다.')
            }

        }
    })
    $.removeCookie('loginToken', {path: '/'})
    window.location.href = '/'
}

//현재 카테고리를 담는 전역변수
let nowCategory = "all"
// 현재 비교모드 상태인지 확인 하는 변수 0일경우 x 1일경우 비교
let comparemode = 0
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
        data: JSON.stringify({
            "category": "all"
        }),
        success: function (response) {
            if (response['result'] === 'success') {
                nowCategory = "all"
                let content = $.parseJSON(response['subs'])
                console.log(content)
                subs.empty()
                main_logo.attr("src", "../assets/img/index/index_main.png")
                selected_sub.css("color", "red")
                $.each(content, function (index, item) {
                    subs.append(`
                        <div class="col-md-6 col-lg-4 mb-5 " onclick=javascript:ToDetail('${item.subscribe_name}')>
                            <div class="portfolio-item mx-auto card">
                                <div
                                    class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                  
                                </div>
                                <img class="img-fluid card-img-top"  src="../assets/img/subs/${item.subscribe_name}.png"
                                     alt="..."/> <!-- 사진 크기 900x650 으로 해야함 -->
                                <div class="sub-title card-body">
                                    <h4 class="card-title">${item.subscribe_name}</h4>
                                
                                </div>
                            </div>
                        </div>
                    `)
                })
                if (comparemode == 1)
                {
                    $('#compareBtn').empty()
                    $('#compareBtn').append
                    (`<br>
                            <div class="text-center mt-4" id="compareBtn">
                                <a class="btn btn-xl btn-outline-primary" onclick="compareMode()" >
                                    <i class="fas fa-download me-2"></i>
                                    비교하기
                                </a>
                            </div>
                   `)
                comparemode = 0
                }

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
        data: JSON.stringify({
            "category": category
        }),
        success: function (response) {
            if (response['result'] === 'success') {
                nowCategory = category
                let content = $.parseJSON(response['subs'])
                console.log(content)
                subs.empty()
                main_logo.attr("src", "../assets/img/index/index_" + category + ".png")

                $.each(content, function (index, item) {
                    subs.append(`
                        <div class="col-md-6 col-lg-4 mb-5 " onclick=javascript:ToDetail('${item.subscribe_name}')>
                            <div class="portfolio-item mx-auto card">
                                <div
                                    class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                  
                                </div>
                                <img class="img-fluid card-img-top"  src="../assets/img/subs/${item.subscribe_name}.png"
                                     alt="..."/> <!-- 사진 크기 900x650 으로 해야함 -->
                                <div class="sub-title card-body">
                                    <h4 class="card-title">${item.subscribe_name}</h4>
                                
                                </div>
                            </div>
                        </div>
                    `)
                })
                if (comparemode == 1)
                {
                    $('#compareBtn').empty()
                    $('#compareBtn').append
                    (`<br>
                        <div class="text-center mt-4" id="compareBtn">
                            <a class="btn btn-xl btn-outline-primary" onclick="compareMode()" >
                                <i class="fas fa-download me-2"></i>
                                비교하기
                            </a>
                        </div>
                     `)
                    comparemode = 0
                }

            }

        }
    })
}


function ToRecommand() {
    request_category.attr("value", nowCategory)
}

// 구독 이미지 클릭시 상세 페이지로 이동

function ToDetail(subscribe_name) {
    window.location.href = "/detail?subscribe_name=" + subscribe_name

}

function compareMode() {
    let subs = $('#subs')

    // category 가 ALL 인 경우
    if (nowCategory == "all") {
        $.ajax({
            type: "POST",
            url: "/api/index",
            contentType: 'application/json',
            data: JSON.stringify({
                "category": nowCategory
            }),
            success: function (response) {
                if (response['result'] === 'success') {
                    let content = $.parseJSON(response['subs'])
                    console.log(content)
                    subs.empty()
                    $.each(content, function (index, item) {
                            subs.append(`
                        <div class="col-md-6 col-lg-4 mb-5 ">
                            <div class="portfolio-item mx-auto card">
                                <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                  
                                </div>
                                <img class="img-fluid card-img-top"  src="../assets/img/subs/${item.subscribe_name}.png"
                                     alt="..."/> <!-- 사진 크기 900x650 으로 해야함 -->
                                <div class="sub-title card-body">
                                    <h4 class="card-title">${item.subscribe_name}</h4>
                                
                                </div>
                            </div>
                            <input class="btn-check" id="check-${item.subscribe_name}" type = "checkbox" name="compare_item" autocomplete="off" value="${item.subscribe_name}">
                            <label class="btn btn-outline-primary" for="check-${item.subscribe_name}">비교선택</label>
                        </div>

                    `)
                        }
                    )

                }

            }
        })

        $('#compareBtn').empty()
        $('#compareBtn').append(`<br>
                 <button class="btn btn-xl btn-outline-primary" onclick="ToCompare()">
                    <i class="fas fa-download me-2"></i>
                    2개를 선택하고 눌러주세요
                </button>
                </form>
        `)
        comparemode = 1
    } else {
        $.ajax({
            type: "POST",
            url: "/api/SelectIndex",
            contentType: 'application/json',
            data: JSON.stringify({
                "category": nowCategory
            }),
            success: function (response) {
                if (response['result'] === 'success') {
                    let content = $.parseJSON(response['subs'])
                    console.log(content)
                    subs.empty()
                    $.each(content, function (index, item) {
                            subs.append(`
                        <div class="col-md-6 col-lg-4 mb-5 ">
                            <div class="portfolio-item mx-auto card">
                                <div class="portfolio-item-caption d-flex align-items-center justify-content-center h-100 w-100">
                                  
                                </div>
                                <img class="img-fluid card-img-top"  src="../assets/img/subs/${item.subscribe_name}.png"
                                     alt="..."/> <!-- 사진 크기 900x650 으로 해야함 -->
                                <div class="sub-title card-body">
                                    <h4 class="card-title">${item.subscribe_name}</h4>
                                
                                </div>
                            </div>
                            <input class="btn-check" id="check-${item.subscribe_name}" type = "checkbox" name="compare_item" autocomplete="off" value="${item.subscribe_name}">
                            <label class="btn btn-outline-primary" for="check-${item.subscribe_name}">비교선택</label>
                        </div>

                    `)
                        }
                    )

                }

            }
        })

        $('#compareBtn').empty()
        $('#compareBtn').append(`<br>
                 <button class="btn btn-xl btn-outline-primary" onclick="ToCompare()">
                    <i class="fas fa-download me-2"></i>
                    2개를 선택해주세요
                </button>
                </form>
        `)
    }
    comparemode = 1

}


function ToCompare() {
    let chkArray = new Array()
    let count = $('input:checkbox[name=compare_item]:checked').length
    let result = ""
    if (count == 2) {
        $('input:checkbox[name=compare_item]:checked').each(function () {
            chkArray.push(this.value)
        })
        let sub1 = chkArray.pop()
        let sub2 = chkArray.pop()
        result = "compare?subscribe1=" + sub1 + "&subscribe2=" + sub2
        window.location.href = result
    } else {
        alert("2개를 선택해주세요.")
    }
}

