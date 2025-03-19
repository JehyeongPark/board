    "use strict";

    let btnWrite = document.getElementById("btnWrite");     // 글쓰기
    let btnDetail = document.querySelectorAll(".btnDetail");  // 상세보기
    /*let btnSrch = document.getElementById("btnSrch");     // 확인*/

    document.addEventListener('DOMContentLoaded', function() {
        addEventListenerCrudBtn();
    });

    function addEventListenerCrudBtn(){
        btnWrite.addEventListener('click', fnBtnWrite);
        btnDetail.forEach(btn => {btn.addEventListener('click', fnBtnDetail);});
        /*btnSrch.addEventListener('click', fnBtnSrch);*/
    }

/* 글쓰기 버튼 */
    function fnBtnWrite(){
        window.location.href = "boardWrite";
    }

/* 상세보기 버튼 */
    function fnBtnDetail() {

        let boardNo = this.getAttribute("data-no"); // 클릭된 항목의 게시글 번호 가져오기
        let popup = window.open(`boardDetail?no=${boardNo}`, "BoardDetailPopup", "width=800,height=600,scrollbars=yes,resizable=yes");

        if (!popup) {
            alert("팝업이 차단되었습니다. 팝업 차단을 해제해주세요.");
        }
    }

/* 확인 버튼 */
/*
    function fnBtnSrch(){

        let selectSrch = document.getElementById("selectSrch").value;
        let writeSrch = document.getElementById("writeSrch").value;

     // 데이터 설정
         let data = {
             selectSrch : selectSrch,
             writeSrch : writeSrch
         };

        // AJAX 요청
        fetch('/boardSrch', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .catch(error => {
            console.error('Error:', error);
            alert('저장 중 오류가 발생했습니다.');
        });
    }
*/
