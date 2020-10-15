package underdogs.devbie.favorite.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.answer.exception.InvalidAuthenticationException;
import underdogs.devbie.favorite.service.FavoriteService;
import underdogs.devbie.user.domain.User;

public abstract class FavoriteController {

    protected FavoriteService favoriteService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devbieToken", required = true, dataType = "String", paramType = "header")})
    @GetMapping(params = {"userId"})
    public ResponseEntity<Object> getMyFavorites(
        @RequestParam Long userId,
        @LoginUser User user
    ) {
        validateUser(userId, user);
        return ResponseEntity.ok(favoriteService.findFavorites(userId));
    }

    private void validateUser(Long userId, User user) {
        if (!user.getId().equals(userId)) {
            throw new InvalidAuthenticationException("인가되지 않는 요청입니다.");
        }
    }

    @PostMapping
    public ResponseEntity<Void> createFavorite(
        @RequestParam String objectType,
        @RequestParam Long objectId,
        @LoginUser User user
    ) {
        favoriteService.createFavorite(objectId, user.getId());

        return ResponseEntity.created(URI.create("/api/favorite/" + objectType + "/" + user.getId())).build();
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Bearer devbieToken", required = true, dataType = "String", paramType = "header")})
    @DeleteMapping
    public ResponseEntity<Void> deleteFavorite(
        @RequestParam Long objectId,
        @LoginUser User user
    ) {
        favoriteService.deleteFavorite(objectId, user.getId());

        return ResponseEntity.noContent().build();
    }
}
