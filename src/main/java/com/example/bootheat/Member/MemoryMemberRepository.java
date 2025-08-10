package com.example.bootheat.Member;

//@Repository   // JPARepository 사용할거임
//public class MemoryMemberRepository implements MemberRepository{
//    // 필드
//    private static Map<Long, Member> store = new HashMap<>();
//    private static long sequence = 0L;
//
//    // 메서드
//    public Member save(Member member) {
//        member.setId(++sequence);
//        store.put(member.getId(), member);
//        return member;
//    }
//    public Optional<Member> findById(Long id) {
//        return Optional.ofNullable(store.get(id));
//    }
//    public Optional<Member> findByName(String name) {
//        for (Member member : store.values()) {
//            if (member.getName().equals(name)) {
//                return Optional.of(member);
//            }
//        }
//        return Optional.empty();
//    }
//    public List<Member> findAll() {
//        return new ArrayList<>(store.values());
//    }
//
//    public void clearStore() {
//        store.clear();
//        sequence = 0L;
//    }
//}
