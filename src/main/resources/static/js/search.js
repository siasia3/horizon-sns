


//검색시 회원 preview API
async function previewMember(keyword){

    const options = {
        noSpinner: true,
        method: 'get'
    };

     let result = await fetchWithAuth(`/api/members/preview?keyword=${keyword}`,options);
     return await result.json();
}

// 디바운스 함수
function debounce(func, delay) {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => func.apply(this, args), delay);
    };
}

//검색시 회원목록 렌더링
function previewMemberUI(previewMembers){
    let searchArea = document.querySelector('.searchArea');
    searchArea.classList.add('searchIng');

    let noSearch = document.querySelector('.noSearch');
    let searchPreview = document.querySelector('.searchPreview');
    let searchResult = document.querySelector('.searchResult');
    searchPreview.style.display = 'block';
    searchResult.replaceChildren();
    if(previewMembers || previewMembers.length !== 0){
        noSearch.style.display = 'none';
        previewMembers.forEach(member => {
            let previewTemplate = document.getElementById('previewTemplate').content.cloneNode(true);
            if(member.profileImage){
                previewTemplate.querySelector('.preview-img').src = member.profileImage;
            }
            previewTemplate.querySelector('.previewNickName').textContent = member.nickname
            previewTemplate.querySelector('.preview').setAttribute('data-member-id',member.id);
            searchResult.appendChild(previewTemplate);
        });
    }
    if(previewMembers.length === 0){
        noSearch.style.display = 'block';
    }
}

//회원목록 UI 없애줌
function deleteSearchUI(){
    let headerCenter = document.getElementById('header-center');
    let searchDIV = headerCenter.querySelector('.searchArea');
    searchDIV.classList.remove('searchIng');
    headerCenter.querySelector('.searchPreview').style.display = "none";
}