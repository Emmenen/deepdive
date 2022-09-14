// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Supply.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Pausable.sol";
import "@openzeppelin/contracts/token/ERC1155/extensions/ERC1155Burnable.sol";
import "@confluxfans/contracts/token/CRC1155/extensions/CRC1155Enumerable.sol";

contract CarbonCreditCard is
  ERC1155Supply,
  Ownable,
  ERC1155Pausable,
  ERC1155Burnable,
  CRC1155Enumerable
{
  uint256 private tokenCount;

  mapping(uint256 => string) private tokenURI;

  string public name = "CarbonCreditCard";
  string public symbol = "CCC";

  event Mint(uint256 tokenId, uint256 amount, address recipient);

  constructor() ERC1155("https://gateway.pinata.cloud/ipfs/") {
    tokenCount = 0;
  }

  function _setTokenURI(string memory tokenURI_, uint256 tokenId_)
    public
    onlyOwner
  {
    require(exists(tokenId_), "TokenID must exist");
    tokenURI[tokenId_] = tokenURI_;
    emit URI(tokenURI_, tokenId_);
  }

  function setURI(string memory newuri) public onlyOwner {
    _setURI(newuri);
  }

  function _tokenURI(uint256 tokenId_) public view returns (string memory) {
    require(exists(tokenId_), "TokenID must exist");
    string memory _toknURI = tokenURI[tokenId_];
    string memory baseURI = uri(tokenId_);
    return string(abi.encodePacked(baseURI, _toknURI));
  }

  //1. 200吨碳排放指标->1 个tokenid amount=200, 同质化
  //2. amount==1;
  function mintNewToken(uint256 amount, string memory hash)
    public
    onlyOwner
    returns (uint256)
  {
    require(amount > 0, "Amount must bigger than 0");
    tokenCount++;
    uint256 newTokenId = tokenCount;
    _mint(msg.sender, newTokenId, amount, "");
    _setTokenURI(hash, newTokenId);
    emit Mint(newTokenId, amount, msg.sender);
    return newTokenId;
  }

  function balanceOfAccountId(address account, uint256[] memory ids)
    public
    view
    returns (uint256[] memory)
  {
    require(
      account != address(0),
      "ERC1155: balance query for the zero address"
    );

    uint256[] memory batchBalances = new uint256[](ids.length);

    for (uint256 i = 0; i < ids.length; ++i) {
      batchBalances[i] = balanceOf(account, ids[i]);
    }

    return batchBalances;
  }

  function balanceOfAccount(address account)
    public
    view
    returns (uint256[] memory)
  {
    require(
      account != address(0),
      "ERC1155: balance query for the zero address"
    );
    require(tokenCount > 0, "Number of Token must be bigger than 0");

    uint256[] memory batchBalances = new uint256[](tokenCount);

    for (uint256 i = 0; i < tokenCount; ++i) {
      batchBalances[i] = balanceOf(account, i + 1);
    }

    return batchBalances;
  }

  function mintExistingToken(uint256 tokenId_, uint256 amount)
    public
    onlyOwner
    returns (uint256)
  {
    require(exists(tokenId_), "TokenID must exist");
    require(amount > 0, "Amount must bigger than 0");
    uint256 newTokenId = tokenId_;
    _mint(msg.sender, newTokenId, amount, "");
    emit Mint(newTokenId, amount, msg.sender);
    return totalSupply(tokenId_);
  }

  function pause() public onlyOwner {
    _pause();
  }

  function unpause() public onlyOwner {
    _unpause();
  }
  function exists(uint256 tokenId) public view override(ERC1155Supply,CRC1155Enumerable) returns (bool){
    return super.exists(tokenId);
  }
  function withdraw() public onlyOwner {
    uint256 balance = address(this).balance;
    require(balance > 0, "No matic left to withdraw");
    (bool success, ) = (msg.sender).call{value: balance}("");
    require(success, "Transfer failed.");
  }

   function _beforeTokenTransfer(
     address operator,
     address from,
     address to,
     uint256[] memory ids,
     uint256[] memory amounts,
     bytes memory data
   ) internal virtual override(CRC1155Enumerable,ERC1155, ERC1155Pausable, ERC1155Supply) {
     super._beforeTokenTransfer(operator, from, to, ids, amounts, data);
   }

  function supportsInterface(bytes4 interfaceId) public view override(ERC1155,CRC1155Enumerable) returns (bool) {
    return super.supportsInterface(interfaceId);
  }

  /**
     * @dev Returns the total amount of tokens for the specified `tokenId`.
     */
  function totalSupply(uint256 tokenId) public view override(ERC1155Supply,CRC1155Enumerable) returns (uint256) {
    return super.totalSupply(tokenId);
  }
}
