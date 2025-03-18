    "use strict";

    let btnWrite = document.getElementById("btnWrite");     // 글쓰기
    let btnDetail = document.querySelectorAll(".btnDetail");  // 상세보기

    document.addEventListener('DOMContentLoaded', function() {
        addEventListenerCrudBtn();
    });

    function addEventListenerCrudBtn(){
        btnWrite.addEventListener('click', fnBtnWrite);
        btnDetail.forEach(btn => {btn.addEventListener('click', fnBtnDetail);});
    }

/* 글쓰기 버튼 */
    function fnBtnWrite(){
        window.location.href = "boardWrite";
    }

/* 상세보기 버튼 */
    function fnBtnDetail(event) {
        event.preventDefault(); // 기본 이벤트(링크 이동) 막기

        let boardNo = this.getAttribute("data-no"); // 클릭된 항목의 게시글 번호 가져오기
        let popup = window.open(`boardDetail?no=${boardNo}`, "BoardDetailPopup", "width=800,height=600,scrollbars=yes,resizable=yes");

        if (!popup) {
            alert("팝업이 차단되었습니다. 팝업 차단을 해제해주세요.");
        }
    }
