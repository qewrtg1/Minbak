document.addEventListener("DOMContentLoaded", function () {
    const accordionBtns = document.querySelectorAll(".accordion-btn");
    const dashboardLink = document.querySelector(".nav-menu > li > a[href='/admin/dashboard']");

    accordionBtns.forEach(btn => {
        btn.addEventListener("click", function () {
            const targetMenu = document.getElementById(this.getAttribute("data-target"));

            // 현재 열려있는 메뉴 닫기 (다른 메뉴 클릭 시 자동 닫힘)
            document.querySelectorAll(".sub-menu").forEach(menu => {
                if (menu !== targetMenu) {
                    menu.style.display = "none";
                    menu.previousElementSibling.classList.remove("active");
                }
            });

            // 클릭한 메뉴 열고 닫기
            if (targetMenu.style.display === "block") {
                targetMenu.style.display = "none";
                this.classList.remove("active");
            } else {
                targetMenu.style.display = "block";
                this.classList.add("active");
            }
        });
    });

    // 대시보드 클릭 시 링크 이동
    if (dashboardLink) {
        dashboardLink.addEventListener("click", function () {
            window.location.href = "/admin/dashboard";
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const menuItems = document.querySelectorAll(".nav-menu > li > a");

    menuItems.forEach((item) => {
        item.addEventListener("click", function (event) {
            // 대시보드는 기본 동작 수행하도록 예외 처리
            if (this.getAttribute("href") !== "/admin/dashboard") {
                event.preventDefault(); // 기본 동작 방지 (페이지 이동 방지)
            }

            // 현재 클릭한 요소가 이미 활성화된 상태인지 확인
            const isActive = this.parentElement.classList.contains("active");

            // 모든 메뉴에서 active 제거
            menuItems.forEach((el) => el.parentElement.classList.remove("active"));

            // 현재 클릭한 메뉴가 활성화된 상태가 아니었다면 active 추가
            if (!isActive) {
                this.parentElement.classList.add("active");
            }
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const mainContent = document.querySelector(".main-content");

    if (mainContent) {
        const header = document.createElement("header");
        header.classList.add("top-bar");

        // 현재 URL에 따라 헤더 제목 변경
        let pageTitle = "대시보드";
        const path = window.location.pathname;

        if (path.includes("/admin/users/list")) {
            pageTitle = "유저 목록";
        } else if (path.includes("/admin/users")) {
            pageTitle = "유저 관리";
        } else if (path.includes("/admin/rooms/list")) {
            pageTitle = "숙소 목록";
        } else if (path.includes("/admin/rooms")) {
            pageTitle = "숙소 관리";
        } else if (path.includes("/admin/bookings")) {
            pageTitle = "예약 관리";
        } else if (path.includes("/admin/reviews")) {
            pageTitle = "리뷰 관리";
        } else if (path.includes("/admin/notices")) {
            pageTitle = "공지사항";
        } else if (path.includes("/admin/messages")) {
            pageTitle = "메시지";
        }

        header.innerHTML = `
            <div class="top-bar-left">
                <span id="current-page-title">${pageTitle}</span>
            </div>
            <div class="top-bar-right">
                <a href="/" class="user-btn">유저 페이지</a>
            </div>
        `;
        mainContent.prepend(header);
    }
});
