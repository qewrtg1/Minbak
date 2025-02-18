document.addEventListener("DOMContentLoaded", function () {
  initTheme();
  initSidebar();
  initModals();
  initAlarmSystem();
});

/* ==============================
  🌙 다크모드 & 라이트모드 토글
================================ */
function initTheme() {
  const savedTheme = getCookie("theme") || "light";
  document.body.classList.add(savedTheme);

  const themeBtn = document.querySelector(".layout-themeBtn");
  themeBtn.classList.add("fa-solid", "fa-moon");
  updateThemeIcon();

  themeBtn.addEventListener("click", toggleTheme);
}

function toggleTheme() {
  const isDark = document.body.classList.toggle("dark");
  document.body.classList.toggle("light", !isDark);
  setCookie("theme", isDark ? "dark" : "light", 7);
  updateThemeIcon();
}

function updateThemeIcon() {
  const themeBtn = document.querySelector(".layout-themeBtn");
  themeBtn.classList.toggle("fa-moon", !document.body.classList.contains("dark"));
  themeBtn.classList.toggle("fa-sun", document.body.classList.contains("dark"));
}

/* ==============================
  🍪 쿠키 관리 함수
================================ */
function setCookie(name, value, days) {
  const date = new Date();
  date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
  document.cookie = `${name}=${value}; expires=${date.toUTCString()}; path=/`;
}

function getCookie(name) {
  return document.cookie
    .split("; ")
    .find((row) => row.startsWith(name + "="))
    ?.split("=")[1] || "";
}

/* ==============================
  📂 사이드바 토글
================================ */
function initSidebar() {
  const savedSidebarState = getCookie("sidebarState");
  savedSidebarState === "collapsed" ? collapseSidebar() : expandSidebar();

  document.querySelector(".layout-sizeBtn").addEventListener("click", toggleSidebar);
}

function toggleSidebar() {
  const isCollapsed = document.querySelector(".layout-logoText").style.width === "0px";
  isCollapsed ? expandSidebar() : collapseSidebar();
  setCookie("sidebarState", isCollapsed ? "expanded" : "collapsed", 7);
}

function collapseSidebar() {
  document.querySelector(".layout-logoText").style.width = "0px";
  document.querySelectorAll(".layout-menuText").forEach((el) => (el.style.width = "0px"));
}

function expandSidebar() {
  document.querySelector(".layout-logoText").style.width = "150px";
  document.querySelectorAll(".layout-menuText").forEach((el) => (el.style.width = "150px"));
}

/* ==============================
  🏆 모달 시스템
================================ */
function initModals() {
  const modals = {
    sideOption: document.querySelector(".layout-sideOptionModal"),
    alarm: document.querySelector(".layout-alarmModal"),
    topOption: document.querySelector(".layout-topOptionModal"),
  };

  document.querySelector(".layout-sideOptionBtn").addEventListener("click", (e) => toggleModal(e, modals.sideOption, modals));
  document.querySelector(".layout-alarmBtn").addEventListener("click", (e) => toggleModal(e, modals.alarm, modals));
  document.querySelector(".layout-userAvatar").addEventListener("click", (e) => toggleModal(e, modals.topOption, modals));

  document.addEventListener("click", () => closeAllModals(modals));
}

function toggleModal(event, modal, modals) {
  event.stopPropagation();
  closeAllModals(modals, modal); // 다른 모달 닫기
  modal.classList.toggle("modal-active"); // 현재 모달 토글
}

function closeAllModals(modals, except = null) {
  Object.values(modals).forEach((modal) => {
    if (modal && modal !== except) modal.classList.remove("modal-active");
  });
}

/* ==============================
  🔔 알람 시스템
================================ */
function initAlarmSystem() {
  updateEmptyState();
  document.querySelectorAll(".layout-alarmCloseBtn").forEach((btn) =>
    btn.addEventListener("click", (event) => closeAlarm(event))
  );

  // 알람 모달에 <a> 태그가 있다면 'messaged' 클래스 추가
  const alarmModal = document.querySelector(".layout-alarmModal");
  if (alarmModal && alarmModal.querySelectorAll("a.layout-modalText").length > 0) {
    document.querySelector(".layout-alarmBtn").classList.add("messaged");
  }
}

function closeAlarm(event) {
  event.preventDefault();
  event.stopPropagation();

  const alarmItem = event.target.closest("a.layout-modalText");
  if (alarmItem) {
    alarmItem.remove();
    updateEmptyState();
  }
}

function updateEmptyState() {
  const alarmModal = document.querySelector(".layout-alarmModal");
  const emptyMessage = alarmModal.querySelector(".layout-empty");
  const hasAlarms = alarmModal.querySelectorAll("a.layout-modalText").length > 0;

  emptyMessage.classList.toggle("empty-active", !hasAlarms);

  // 알람이 없을 경우 'messaged' 클래스 제거
  if (!hasAlarms) {
    document.querySelector(".layout-alarmBtn").classList.remove("messaged");
  }
}