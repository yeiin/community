package com.ktb.community.repository.post_stats;

import com.ktb.community.domain.post_stats.PostStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostStatsJpaRepository extends JpaRepository<PostStats, Long> {
}
