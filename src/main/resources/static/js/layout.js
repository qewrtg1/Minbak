document.addEventListener("DOMContentLoaded", () => {
  const barBtn = document.querySelector(".barBtn");
  const sidebarTexts = document.querySelectorAll(".sidebarText");

  // 🍪 쿠키 설정 함수
  function setCookie(name, value, days) {
    const expires = new Date();
    expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);
    document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
  }

  // 🍪 쿠키 가져오는 함수
  function getCookie(name) {
    const cookies = document.cookie.split("; ");
    for (const cookie of cookies) {
      const [cookieName, cookieValue] = cookie.split("=");
      if (cookieName === name) return cookieValue;
    }
    return null;
  }

  // 1️⃣ 모든 sidebarText 요소의 transition을 비활성화 (애니메이션 제거)
  sidebarTexts.forEach((sidebarText) => {
    sidebarText.style.transition = "none";
  });

  // 2️⃣ 쿠키에서 사이드바 상태 불러오기
  const isCollapsed = getCookie("sidebarCollapsed") === "true";

  // 3️⃣ 상태 적용 (애니메이션 없이 즉시 적용)
  sidebarTexts.forEach((sidebarText) => {
    sidebarText.style.width = isCollapsed ? "0px" : "140px";
  });

  // 4️⃣ DOM이 완전히 로드된 후 transition 다시 활성화 (애니메이션 적용)
  setTimeout(() => {
    sidebarTexts.forEach((sidebarText) => {
      sidebarText.style.transition = "width 0.3s ease";
    });
  }, 10); // 짧은 지연 시간 후 활성화

  // 5️⃣ 버튼 클릭 시 애니메이션 적용하여 토글
  barBtn.addEventListener("click", () => {
    const currentCollapsed = sidebarTexts[0].style.width === "0px";

    sidebarTexts.forEach((sidebarText) => {
      sidebarText.style.width = currentCollapsed ? "140px" : "0px";
    });

    // 변경된 상태를 쿠키에 저장
    setCookie("sidebarCollapsed", !currentCollapsed, 7);
  });
});
