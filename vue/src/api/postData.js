import fileFetch from '@/config/fileFetch'
import fetch from '@/config/fetch'


export const uploadImageFile = (filePatch) => fileFetch('/uploadImageFile',filePatch);

export const uploadMetaData = (metaDataForm) => fetch('/uploadMetaData',metaDataForm,"POST");

export const burn = (data) => fetch('/burn',data,"POST");

// export const uploadMetaData = (metaDataForm) => fetch('/uploadMetaData',metaDataForm,"POST");
