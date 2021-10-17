// Jquery Import
src = "https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"


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

function setUserInfo() {
    let userInfo = $('#user-info')
    let token = $.cookie('loginToken')
    console.log(token)

    $.ajax({
        type: "POST",
        url: "/index",
        headers: {'authorization': `Bearer ${token}`},
        data: {},
        success: function (response) {
            if (response['result'] === 'success') {
                // id 를 전역변수 처리 하겠습니다.
                id = response['id']
                userInfo.append(`
                        <a class="navbar-brand" href="#">${id}</a>
                        <a class="btn btn-secondary" onclick="logOut()">Log Out</a>
                    `)
            } else {
                alert('다시 로그인해주세요.')
                window.location.href = '/login'
            }
        }
    })


}
