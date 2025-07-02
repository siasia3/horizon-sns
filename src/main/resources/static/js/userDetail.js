

function loadUserInfo(nickname){
    const options = {
        method: 'GET'
    };
    return fetchWithAuth(`/api/search/user/${nickname}`,options)
        .then(response => response.json());
}

let userPage = 0;
let isUserPostFetching = false;
let hasMoreUserPosts = true;
const userPostSize = 12;

async function pagingUserPosts(nickname){
    if (!hasMoreUserPosts || isUserPostFetching) return;
    isUserPostFetching = true;
    const options = {
        method: 'GET'
    };
    let response = await fetchWithAuth(`/api/user/${nickname}/posts?page=${userPage}&size=${userPostSize}`,options);
    let data = await response.json();
    userPage += 1;
    hasMoreUserPosts = data.hasNext;
    isUserPostFetching = false;
    return data;
}

//회원정보 렌더링
function renderUserInfo(userData){
    if(userData.profilePath){
        document.querySelector('.memberProfile').src = userData.profilePath;
    }
    document.getElementById('userInfo').setAttribute('data-member-id',userData.memberId);
    let userId = sessionStorage.getItem('userId');
    if(userId==userData.memberId){
        document.querySelector('.myProfileBtn').style.display = 'block';
    }
    if(userId!=userData.memberId){
        document.querySelector('.userProfileBtn').style.display = 'flex';
    }
    document.querySelector('.profileName').textContent = userData.nickName;
    document.getElementById('friendName').textContent = userData.nickName;
    document.querySelector('.postCnt').textContent = userData.postCnt;
    document.querySelector('.friendCnt').textContent = userData.friendCnt;
}

//회원게시글 렌더링
function renderUserPosts(userPosts){
    let postArea = document.getElementById('userPostInner');
    let thumbnailTemplate = document.getElementById('post-thumbnail-template');
    if(!userPosts.content || userPosts.content.length === 0){
        postArea.style.justifyContent = 'center';
        let noPost = document.getElementById('no-post-template').content.cloneNode(true);
        postArea.appendChild(noPost);
    }
    userPosts.content.forEach((userPost)=>{
        let postThumbnail = thumbnailTemplate.content.cloneNode(true);
        postThumbnail.querySelector('.memberPost').setAttribute('data-post-id',userPost.postId);
        renderMediaElementByExtension(userPost.thumbnailPath,postThumbnail);
        postThumbnail.querySelector('.likeCnt').textContent = userPost.likeCount;
        postThumbnail.querySelector('.commentCnt').textContent = userPost.commentCount;
        postArea.appendChild(postThumbnail);
    });
}
//게시글 파일 확장자를 통한 렌더링
function renderMediaElementByExtension(fileUrl,template){
    const extension = fileUrl.split('.').pop().toLowerCase();

    if (["mp4", "webm","mov","avi"].includes(extension)) {
        template.querySelector('img.postMedia').remove();
        template.querySelector('video.postMedia').src = fileUrl;
    } else if (["jpg", "png", "jpeg", "gif", "jfif", "webp"].includes(extension)) {
        template.querySelector('video.postMedia').remove();
        template.querySelector('img.postMedia').src = fileUrl;
    } else {
        console.warn("지원되지 않는 확장자입니다:", extension);
        template.querySelector('video.postMedia').remove();
    }
}

