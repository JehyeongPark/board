    "use strict";
    let btnSave = document.getElementById("btnSave");
    let btnClose = document.getElementById("btnClose");


    document.addEventListener('DOMContentLoaded', function() {
        addEventListenerCrudBtn();
    });


    function addEventListenerCrudBtn(){
        btnSave.addEventListener('click', fnBtnSave);
        btnClose.addEventListener('click', fnBtnClose);
    }


/* ===================== 유효성 체크 ===================== */
    function fnChkValid(){
        let title = document.getElementById("writeTitle").value.trim();         // 글 제목
        let content = document.getElementById("writeContent").value.trim();     // 글 내용

    // 제목
        if(title ===""){
            alert("제목을 입력하세요.");
            document.getElementById("writeTitle").focus();
            return false;
        }

    // 내용
        if(content ===""){
            alert("내용을 입력하세요.");
            document.getElementById("writeContent").focus();
            return false;
        }

        return true;
    }

/* 저장 버튼 */
    function fnBtnSave(){

        // 유효성 검사
        if(!fnChkValid()){
            return;
        }

        let title = document.getElementById("writeTitle").value;      // 글 제목
        let content = document.getElementById("writeContent").value;  // 글 내용

        // 데이터 설정
        let data = {
            title: title,
            comment: content
        };

        // AJAX 요청
        fetch('/boardInsert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            console.log('Success:', result);
            alert('저장이 완료되었습니다.');
            window.location.href = "/";
        })
        .catch(error => {
            console.error('Error:', error);
            alert('저장 중 오류가 발생했습니다.');
        });
    }

/* 닫기 버튼 */
    function fnBtnClose(){
        window.location.href = "/";
    }
